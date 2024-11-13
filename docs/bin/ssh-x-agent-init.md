% ssh-x-agent-init(1) Version Latest | Start one agent instance only


# NAME


`ssh-x-agent-init`:
* start only one SSH agent instance (by default SSH starts an agent by session)
* and [load keys](#key-loading) protected or not


# ADVANTAGE

It creates only one ssh-agent process per system,
rather than the norm of one ssh-agent per login session.
You only need to enter a passphrase once every time your machine is rebooted.


# USAGE: Start only one agent instance


This script is a wrapper around `ssh-agent -a`
```bash
# in place of
eval "$(ssh-agent -a)"
# you would use
eval "$(source ssh-agent-init)"
```

You would place this script in a start script such as `.bashrc`.

# USAGE: Add your key

To load the protected key `id_my_key` automatically
with the passphrase `secret`


```bash
export SSH_X_KEY_PASSPHRASE_ID_MY_KEY=secret
eval "$(ssh-agent-init)"
```

For a detailed explanation on how it works, see the [key loading section](#key-loading)

In `Windows System Linux (WSL)`, you need to add the env to the `WSLENV` to pass it to `WSL`
Example:
```
WSLENV=SSH_X_KEY_PASSPHRASE_ID_MY_KEY
```


## SYNOPSIS

${SYNOPSIS}

## KEY LOADING

This command will load keys that are:
* non-protected
* protected where the passphrase is defined by env variables

**How it works?**

The function will loop through the environment variables with the `SSH_X_KEY_PASSPHRASE` prefix.

Syntax:
```bash
export SSH_X_KEY_PASSPHRASE_KeyName=KeyPassphrase
```

Example:
If the function finds the env `SSH_X_KEY_PASSPHRASE_MY_KEY`, it will:
* load the key located at `~/.ssh/my_key`
* add it with the value of `SSH_X_KEY_PASSPHRASE_MY_KEY` as passphrase

## REF

* Idea based on [auto-launching-ssh-agent-on-git-for-windows](https://docs.github.com/en/authentication/connecting-to-github-with-ssh/working-with-ssh-key-passphrases#auto-launching-ssh-agent-on-git-for-windows)
* Project with same functionality: [keychain](https://github.com/funtoo/keychain)

## ENV

See [ssh-x-env](ssh-x-env.md)

