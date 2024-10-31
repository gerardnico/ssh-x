% ssh-x-auth-proxy-command(1) Version Latest | Proxy command to load private keys from public key

# SSH Auth Proxy command

## About

The `SSH Auth Proxy command` is a command that will load your private key
if you use a public key as identity.


## Example

### Load a private key stored on the file system for one Host

With this `~/.ssh/conf` configuration
```conf
Host github.com
    # Public key as identity
    IdentityFile ~/.ssh/id_git_github.pub
    # Proxy command
    ProxyCommand ssh-x-auth-proxy-command %h %n %p %r
    # Lifetime
    AddKeysToAgent 15m
    # Generally needed (ie don't try to use ssh-agent identity/keys)
    IdentitiesOnly yes
```
`ssh-x-auth-proxy-command` will:
* check if the private key of the public key `~/.ssh/id_git_github.pub` is present (ie loaded in the agent)
* if not, it will add the private key located at `~/.ssh/id_git_github` with a lifetime of `15 minutes`
* if the key is protected, it will ask for the passphrase

### Load a private key stored in the pass password manager store 

With the same configuration as [previously](#load-a-private-key-stored-on-the-file-system-for-one-host)
but with the [pass manager as key store](#key-store)

Change the [key store](#key-store) to `pass` by setting the following environment variable in your `.bashrc`
```bash
export SSHX_KEY_STORE=pass
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
    ProxyCommand ssh-x-auth-proxy-command %h %n %p %r
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
    ProxyCommand ssh-x-auth-proxy-command %h %n %p %r
    # Your identity file should be a Public Key
    IdentityFile ~/.ssh/id_git_github.pub
```

## Prerequisites

Your [Identity](https://man.openbsd.org/ssh_config#IdentityFile) should be a public key

## Key Store

We support 2 keys store:
* `file`: where the key is stored in a file system directory (default)
* `pass`: where the key is stored in the [pass password manager](https://www.passwordstore.org/)

You choose your key store with the variable [SSHX_KEY_STORE](#environment)

For the 2 stores, the key is located at `/KeyName` where :
* [SSHX_KEY_HOME](#environment) defines the directory location.
* `KeyName` is the name of the private key (ie the public key without the `.pub` extension)


## Environment

* `SSHX_KEY_STORE`: the name of the [key store](#key-store) that contains the private key. Possible values:
  * `file`: (default) for the file system
  * `pass`: for the [pass password manager](https://www.passwordstore.org/)
* `SSHX_KEY_HOME`: a path to the directory containing your private keys. Default to:
  * `~/.ssh` for a [file key store](#key-store)
  * `ssh` for the [pass key store](#key-store)
* `SSHX_LIFE`: the default lifetime of the loaded keys (Default to `15m`) Used if a time interval is not found in the `AddKeysToAgent` conf.
* `SSH_ASKPASS`: to set how to retrieve the password. By default, we prompt with [ssh-x-askpass-prompt](ssh-x-askpass-prompt)


## How to 

### How to add your private key to pass

To add the private key `~/.ssh/id_git_github.com` into pass at `ssh/id_git_github.com`,
you would use this command:

```bash
cat ~/.ssh/id_git_github.com | pass insert -m "ssh/id_git_github.com";
```

