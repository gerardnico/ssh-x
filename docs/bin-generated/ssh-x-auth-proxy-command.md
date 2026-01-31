% ssh-x-auth-proxy-command(1) Version Latest | Proxy command to load private keys from public key

# SSH Auth Proxy command

## About

The `SSH Auth Proxy command` is a command that will load your private key
if you use a public key as identity.


## Example

### Load a private key stored on the file system for one Host

With the example below, `ssh-x-auth-proxy-command` will:
* check if the private key of the public key `~/.ssh/id_git_github.pub` is present (ie loaded in the agent)
* if not, it will add the private key located at `~/.ssh/id_git_github` with a lifetime of `15 minutes`
* if the key is protected, it will ask for the passphrase

With this `~/.ssh/config` configuration
```conf
Host github.com
    # Public key as identity
    IdentityFile ~/.ssh/id_git_github.pub
    # Proxy command
    ProxyCommand env BASHLIB_LIBRARY_PATH=/path/to/bash-lib/lib /path/to/ssh-x-auth-proxy-command %h %n %p %r
    # Lifetime
    AddKeysToAgent 15m
    # Generally needed (ie don't try to use ssh-agent identity/keys)
    IdentitiesOnly yes
```
where:

Note that `env BASHLIB_LIBRARY_PATH=/path/to/bash-lib/lib` uses the [env command](https://en.wikipedia.org/wiki/Env) to set the library location of [bash lib](https://github.com/gerardnico/bash-lib) 
and is mandatory for editor as they don't take any Shell environment variable.



### Load a private key stored in the pass password manager store 

With the same configuration as [previously](#load-a-private-key-stored-on-the-file-system-for-one-host)
but with the [pass manager as key store](ssh-x-env.md#key-store)

Change the [key store](ssh-x-env.md#key-store) to `pass` by setting the following environment variable in your `.bashrc`
```bash
export SSH_X_KEY_STORE=pass
```
With this environment, `ssh-x-auth-proxy-command` will:
* check if the private key of the public key `~/.ssh/id_git_github.pub` is present (ie loaded in the agent)
* if not, it will add the private key located at `ssh/id_git_github` with a lifetime of `15 minutes`

Because the private key is in the [pass manager](https://www.passwordstore.org/), the key
is already protected and would have no passphrase but if this is the case, we still ask for the passphrase

### How to configure for all hosts
This example shows you how you can configure `ssh` to
use `ssh-x-auth-proxy-command` for all hosts thanks to the SSH template features.

For the `IdentityFile`, `ssh` supports the following placeholder:
* `%h` : The remote hostname
* `%n` : The original remote hostname
* `%p` : The remote port
* `%r` : The remote username
* 


Example in the `~/.ssh/conf`
```conf
Host *
    # Public key as identity
    IdentityFile ~/.ssh/id_%r_%h.pub
    # Proxy command
    ProxyCommand env BASHLIB_LIBRARY_PATH=/path/to/bash-lib/lib /path/to/ssh-x-auth-proxy-command %h %n %p %r
    # Lifetime
    AddKeysToAgent 30m
    # Generally needed (ie don't try to use ssh-agent identity/keys)
    IdentitiesOnly yes
```


If you use `github`:
* the `IdentityFile` file would be `~/.ssh/id_git_github.com.pub`
* the corresponding `Private Key` file would be located:
  * on the file system at: `~/.ssh/id_git_github.com`
  * in `pass` at: `ssh/id_git_github.com`
* the lifetime for the key would be `30 minutes` 

## Usage

```bash
ssh-x-auth-proxy-command dnsHostname sshHostName port user
# in the `~/.ssh/conf` with placeholder
ssh-x-auth-proxy-command %h %n %p %r
```

You use it in the [ProxyCommand](https://man.openbsd.org/ssh_config#ProxyCommand) conf of your SSH configuration file (`~/.ssh/conf`)
```conf
Host *
    # Proxy command
    ProxyCommand env BASHLIB_LIBRARY_PATH=/path/to/bash-lib/lib /path/to/ssh-x-auth-proxy-command %h %n %p %r
    # Your identity file should be a Public Key
    IdentityFile ~/.ssh/id_git_github.pub
```

Note that `env BASHLIB_LIBRARY_PATH=/path/to/bash-lib/lib` set the library location of [bash lib](https://github.com/gerardnico/bash-lib)
and is mandatory for editor as they don't take over any Shell environment variable.

## Prerequisites

Your [Identity](https://man.openbsd.org/ssh_config#IdentityFile) should be a public key



## Environment

See `ssh-x-env(1)`


## How to know why I get an intempestive pinentry dialog?

When implementing this proxy, you may get intempestive pinentry dialog asking you 
for your master password.

If you want to discover from where they are coming from, set the log file [SSH_X_CALLERS_LOG env](ssh-x-env.md).
Example
```conf
Host github.com
     ProxyCommand env BASHLIB_LIBRARY_PATH=/path/to/bash-lib/lib SSH_X_CALLERS_LOG=/tmp/ssh-x-auth-proxy-callers.log /path/to/ssh-x-auth-proxy-command %h %n %p %r
```
You will get session recording such as:
```
SESSION_TIME          PID     CMDLINE
2024-11-16 20:23:42 - 31980 - /usr/bin/ssh -o SendEnv=GIT_PROTOCOL git@github.com git-upload-pack 'gerardnico/ssh-x.git'
2024-11-16 20:23:42 - 31979 - git -c color.ui=always fetch
2024-11-16 20:23:42 - 31978 - /bin/bash /home/admin/code/git-x/bin/git-exec fetch
2024-11-16 20:23:42 - 31656 - /bin/bash /home/admin/code/git-x/bin/git-exec fetch
2024-11-16 20:23:42 - 31655 - /usr/bin/git exec fetch
2024-11-16 20:23:42 - 31654 - bash /usr/local/sbin/git exec fetch
2024-11-16 20:23:42 - 2633 - -bash
2024-11-16 20:23:42 - 2632 - /init
2024-11-16 20:23:42 - 2631 - /init
2024-11-16 20:23:42 - 1 - /init
```
where:
* the first column `SESSION_TIME` is the session time (ie one time = one call)
* the second column `PID` is the process id
* the third column `CMDLINE` is the command line


## How to add your private key to pass

To add the private key `~/.ssh/id_git_github.com` into pass at `ssh/id_git_github.com`,
you would use this command:

```bash
# Private Key
cat ~/.ssh/id_git_github.com | pass insert -m "ssh-x/id_git_github.com";
# If you may want to add the public key as well
cat ~/.ssh/id_git_github.com.pub | pass insert -m "ssh-x/id_git_github.com.pub"
```

