# SSH-X  - A collection of SSH utilities


## About

`SSH-X` is a collection of utilities around [OpenSSH](https://www.openssh.com/) 

## List

* [ssh-x](docs/bin-generated/ssh-x.md) - The main entry of all `ssh-x` utilities.
  * [ssh-x-agent-init](docs/bin-generated/ssh-x-agent-init.md) - Start a singleton SSH agent and load private keys automatically
  * [ssh-x-env](docs/bin-generated/ssh-x-env.md) - Print the `SSH` and `SSH-X` environment
  * `ssh-x info` - Get a 360 view of your SSH installation.
  * [ssh-x-key](docs/bin-generated/ssh-x-key.md) - Manage your keys
  * [ssh-x-env](docs/bin-generated/ssh-x-env.md) - Get the environment information
  * [ssh-x-key-passphrase](docs/bin-generated/ssh-x-key-passphrase.md) - Add or remove a key passphrase
* [ssh-x-auth-proxy-command](docs/bin-generated/ssh-x-auth-proxy-command.md) - An Ssh `ProxyCommand` that load automatically your private keys when you use your public key as identity
* [SSH_ASKPASS](https://man.openbsd.org/ssh.1#SSH_ASKPASS) program
  * [ssh-x-askpass-prompt](docs/bin-generated/ssh-x-askpass-prompt.md) - Prompt for a secret from a terminal or GUI
  * [ssh-x-askpass-env](docs/bin-generated/ssh-x-askpass-env.md) - Echo a secret from an environment variable


## Installation

### HomeBrew

You can install `ssh-x` with [homebrew](https://brew.sh/)
```bash
brew install --HEAD gerardnico/tap/sshx
```
Then set the `BASHLIB_LIBRARY_PATH` in your bashrc
```bash
export BASHLIB_LIBRARY_PATH=$(brew --prefix bashlib)/lib
```

### WSL Git IDE and SSH Agent integration

Because this library uses the linux `ssh-agent` (ie wsl), 
it's not possible to pass the ssh-agent env (ie process id) directly with `WSLENV`.
Git therefore does not know that there is an agent running.

The solution is to:
* create a `git` wrapper at `/usr/local/sbin/git`
* set this `git` executable as your git executable in your IDE


Example:
```bash
#!/usr/bin/env bash

# Load ssh-agent env
SSH_X_AGENT_ENV=$HOME/.ssh/ssh-x-agent.env
if [ -f "$SSH_X_AGENT_ENV" ]; then
  source "$SSH_X_AGENT_ENV" >| /dev/null
fi

# Other SSH_X_ENV or source your env file.
export SSH_X_KEY_STORE="pass"

# Then git
/usr/bin/git "$@"
```

Test from a Windows terminal
```bash
wsl --cd /path/to/a/git/repo git fetch origin
```

Example of a console git fetch with IntelliJ and [pass as a private key store](docs/bin-generated/ssh-x-env.md#key-store)
```bash
19:30:39.727: [touch] /usr/local/sbin/git -c credential.helper= -c core.quotepath=false -c log.showSignature=false fetch origin --recurse-submodules=no --progress --prune
```
Output:
```
ssh-x-auth-proxy-command::main#71: Private Key is not in agent
ssh-x-auth-proxy-command::main#91: Adding Pass Private Key (ssh-x/id_git_github.com) to agent for a lifetime of 15m seconds
Identity added: /dev/fd/63 (email@email.com)
Lifetime set to 900 seconds
```

### WSL IDE and Pass integration

If you use [pass as a private key store](docs/bin-generated/ssh-x-env.md#key-store), you need to install
a pgp gui pinentry.

Example with `pinentry-qt`.
```bash
sudo apt -y install pinentry-qt
```

## Support

### Permission denied (publickey)

If you get this error from your IDE, this is because
Git does not know your agent.
See [WSL GIT IDE Integration](#wsl-git-ide-integration)