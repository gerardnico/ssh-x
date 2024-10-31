% ssh-x-agent-init(1) Version Latest | Start one agent instance only


# NAME


`ssh-x-agent-init`:
* start only one SSH agent instance (by default SSH starts an agent by session)
* and [load keys](#key-loading) protected or not


## EXAMPLE / USAGE

This is used in your `.bashrc` or env loading script

2 env variables are needed.
* The location of the env file `SSHX_ENV`
* The location of the agent socket file `SSH_AUTH_SOCK`

For the key usage, see the [add_keys function](#key-loading)


```bash
export SSHX_ENV="$HOME"/.ssh/ssh-x-agent.env
export SSH_AUTH_SOCK="$HOME"/.ssh/agent.sock
export SSH_KEY_PASSPHRASE_MY_KEY=secret
ssh-x-agent-init
```

## SYNOPSIS

${SYNOPSIS}

## KEY LOADING

This command will load keys that are:
* non-protected
* protected where the passphrase is defined by env variables

**How it works?**

The function will loop through the environment variables with the `SSH_KEY_PASSPHRASE` prefix.

When it finds an env such as `SSH_KEY_PASSPHRASE_MY_KEY`, the function will:
* try to find a file at `~/.ssh/my_key`
* add it with the value of `SSH_KEY_PASSPHRASE_MY_KEY` as passphrase

## REF

* Idea based on [auto-launching-ssh-agent-on-git-for-windows](https://docs.github.com/en/authentication/connecting-to-github-with-ssh/working-with-ssh-key-passphrases#auto-launching-ssh-agent-on-git-for-windows)
* Project with same functionality: [keychain](https://github.com/funtoo/keychain)