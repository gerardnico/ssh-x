% ssh-auth-proxy-command(1) Version Latest | Proxy command to load private keys from public key

# SSH Auth Proxy command

## About

The `SSH Auth Proxy command` is a command that will load your private key
if you set a public key id as identity.

You set it in the `ProxyCommand` conf of your SSH configuration file (`~/.ssh/conf`)

## Usage and example

An example that add a public key as identity for all hosts. 

In the `~/.ssh/conf`
```conf
Host *
    # Public key as identity
    IdentityFile ~/.ssh/id_%r_%h.pub
    # Proxy command
    ProxyCommand ssh-auth-proxy-command %h %n %p %r
    # Lifetime
    AddKeysToAgent 15m
    # Generally needed (ie don't try to use ssh-agent identity/keys)
    IdentitiesOnly yes
```
where the following placeholder means:
* `%h` : The remote hostname
* `%n` : The original remote hostname
* `%p` : The remote port
* `%r` : The remote username



## Conf

* `SSHX_KEY_HOME`: a path to the directory containing your private keys. Default to `~/.ssh`
* `SSHX_LIFE`: the default lifetime of the loaded keys (Default to `15m`) Used if a time interval is not found in the `AddKeysToAgent` conf.
