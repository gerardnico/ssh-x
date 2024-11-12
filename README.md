# SSH-X  - A collection of SSH utilities


## About

`SSH-X` is a collection of utilities around [OpenSSH](https://www.openssh.com/) 

## List

* [ssh-x](build/docs/bin/ssh-x.md) - The main entry of all `ssh-x` utilities.
  * [ssh-x-agent-init](build/docs/bin/ssh-x-agent-init.md) - Start a singleton SSH agent and load private keys automatically
  * [ssh-x-env](build/docs/bin/ssh-x-env.md) - Print the `SSH` and `SSH-X` environment
  * `ssh-x info` - Get a 360 view of your SSH installation.
  * [ssh-x-key](build/docs/bin/ssh-x-key.md) - Manage your keys
  * [ssh-x-env](build/docs/bin/ssh-x-env.md) - Get the environment information
  * [ssh-x-key-passphrase](build/docs/bin/ssh-x-key-passphrase.md) - Add or remove a key passphrase
* [ssh-x-auth-proxy-command](build/docs/bin/ssh-x-auth-proxy-command.md) - An Ssh `ProxyCommand` that load automatically your private keys when you use your public key as identity
* [SSH_ASKPASS](https://man.openbsd.org/ssh.1#SSH_ASKPASS) program
  * [ssh-x-askpass-prompt](build/docs/bin/ssh-x-askpass-prompt.md) - Prompt for a secret from a terminal or GUI
  * [ssh-x-askpass-env](build/docs/bin/ssh-x-askpass-env.md) - Echo a secret from an environment variable


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

### WSL IDE integration

If you want to use an IDE that open a WSL directory (ie use `wsl` as interface),
you need to add extra environment to set the non-interactive environment.

You should do the following on your Windows host:
* Set the env `BASHLIB_LIBRARY_PATH` to your Windows env.
```bash
# example
BASHLIB_LIBRARY_PATH=/home/linuxbrew/.linuxbrew/opt/bashlib/lib
# The value should be the same in interactive mode (bashrc) and windows
```
* Add `BASHLIB_LIBRARY_PATH` to `WSLENV` to path the env to `wsl`
```bash
WSLENV=OTHERENV:BASHLIB_LIBRARY_PATH
```

You can check that it's working by executing:
```bash
# env test
wsl ssh-x-env 
```

If it does not work, check that you have set your `BASHLIB_LIBRARY_PATH` env correctly.
```bash
wsl env | wsl grep BASHLIB_LIBRARY_PATH=
```

### WSL Git test
```bash
wsl --cd /path/to/a/git/repo git fetch origin
```
Conf: 
```bash
git clone -c "core.sshCommand=ssh -i ~/.ssh/id_rsa_example -F /dev/null" 
GIT_SSH_COMMAND="ssh -i ~/.ssh/id_rsa_example -o 'IdentitiesOnly yes'"
GIT_SH=test in /etc/profile.d/git.sh
```

