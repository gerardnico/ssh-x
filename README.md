# SSH-X  - A collection of SSH utilities


## About

`SSH-X` is a collection of utilities around [OpenSSH](https://www.openssh.com/) 

## List

* [ssh-x](build/docs/bin/ssh-x.md) - A ssh wrapper to manage common ssh operations without knowing all whistle.
  * [ssh-x-agent-init](build/docs/bin/ssh-x-agent-init.md) - Start a singleton SSH agent and load private keys automatically
  * [ssh-x-env](build/docs/bin/ssh-x-env.md) - Print the `SSH` and `SSH-X` environment
  * `ssh-x info` - Get a 360 view of your SSH installation.
  * [ssh-x-key](build/docs/bin/ssh-x-key.md) - Manage your keys
  * [ssh-x-key-passphrase](build/docs/bin/ssh-x-key-passphrase.md) - Add or remove a key passphrase
* [ssh-x-auth-proxy-command](build/docs/bin/ssh-x-auth-proxy-command.md) - An Ssh `ProxyCommand` that load automatically your private keys when you use your public key as identity
* [ssh-x-askpass](build/docs/bin/ssh-x-askpass.md) - A `SSH_ASKPASS` implementation to ask a secret from a terminal
