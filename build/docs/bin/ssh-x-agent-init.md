% ssh-x-agent-init(1) Version Latest | Start one agent instance only


# NAME


`ssh-x-agent-init`:
* start only one SSH agent instance (by default SSH starts an agent by session)
* and [load keys](#key-loading) protected or not



# USAGE: Start only one agent instance


This script is a wrapper around `ssh-agent -a`
```bash
# in place of
eval $(ssh-agent -a)
# you would use
eval $(ssh-agent-init)
```

You would place this script in a start script such as `.bashrc`.

# USAGE: Add your key

To load the protected key `id_my_key` automatically
with the passphrase `secret`


```bash
export SSH_KEY_PASSPHRASE_ID_MY_KEY=secret
eval $(ssh-agent-init)
```

For a detailed explanation on how it works, see the [key loading section](#key-loading)

In `Windows System Linux (WSL)`, you need to add the env to the `WSLENV`
```
WSLENV=SSH_KEY_PASSPHRASE_ID_MY_KEY
```


## SYNOPSIS

```bash
ssh-x-agent-init
```

## KEY LOADING

This command will load keys that are:
* non-protected
* protected where the passphrase is defined by env variables

**How it works?**

The function will loop through the environment variables with the `SSH_KEY_PASSPHRASE` prefix.

Syntax:
```bash
export SSH_KEY_PASSPHRASE_KeyName=KeyPassphrase
```

Example:
If the function finds the env `SSH_KEY_PASSPHRASE_MY_KEY`, it will:
* load the key located at `~/.ssh/my_key`
* add it with the value of `SSH_KEY_PASSPHRASE_MY_KEY` as passphrase

## REF

* Idea based on [auto-launching-ssh-agent-on-git-for-windows](https://docs.github.com/en/authentication/connecting-to-github-with-ssh/working-with-ssh-key-passphrases#auto-launching-ssh-agent-on-git-for-windows)
* Project with same functionality: [keychain](https://github.com/funtoo/keychain)

## ENV

* `SSH_X_AGENT_ENV`: the location where the env file is saved (Default to `~/.ssh/ssh-x-agent.env`)
* `SSH_X_KEY_HOME`: a path to the directory containing your private keys. Default to `~/.ssh`
