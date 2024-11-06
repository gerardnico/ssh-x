# SSH-X  - A collection of SSH utilities


## About

`SSH-X` is a collection of utilities around [OpenSSH](https://www.openssh.com/) 

## List

* [ssh-x](build/docs/bin/ssh-x.md) - The main entry of all `ssh-x` utilities.
  * [ssh-x-agent-init](build/docs/bin/ssh-x-agent-init.md) - Start a singleton SSH agent and load private keys automatically
  * [ssh-x-env](build/docs/bin/ssh-x-env.md) - Print the `SSH` and `SSH-X` environment
  * `ssh-x info` - Get a 360 view of your SSH installation.
  * [ssh-x-key](build/docs/bin/ssh-x-key.md) - Manage your keys
  * [ssh-x-key-passphrase](build/docs/bin/ssh-x-key-passphrase.md) - Add or remove a key passphrase
* [ssh-x-auth-proxy-command](build/docs/bin/ssh-x-auth-proxy-command.md) - An Ssh `ProxyCommand` that load automatically your private keys when you use your public key as identity
* [SSH_ASKPASS](https://man.openbsd.org/ssh.1#SSH_ASKPASS) program
  * [ssh-x-askpass-prompt](build/docs/bin/ssh-x-askpass-prompt.md) - Prompt for a secret from a terminal or GUI
  * [ssh-x-askpass-env](build/docs/bin/ssh-x-askpass-env.md) - Echo a secret from an environment variable
