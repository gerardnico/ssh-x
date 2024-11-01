% ssh-x-env(1) Version Latest | SSH-X Env


# NAME

`ssh-x-env` - Print the env in an export format


## SYNOPSIS

Print the env for SSH. You would use it in your script to set the SSH environment.
```bash
eval $(ssh-x-env)
```


## ENV

### SSH


* `SSH_AUTH_SOCK`: The location of the agent socket Default to `/home/admin/.ssh/agent.sock`
* `SSH_ASKPASS` to set how to retrieve the password. 
  * Default to [ssh-askpass](https://man.openbsd.org/ssh-askpass.1) if present 
  * otherwise, we prompt with [ssh-x-askpass-prompt](ssh-x-askpass-prompt) 

### SSH-X

* `SSH_X_KEY_STORE`: the name of the [key store](#key-store) that contains the private key. Possible values:
    * `file`: (default) for the file system
    * `pass`: for the [pass password manager](https://www.passwordstore.org/)
* `SSH_X_KEY_HOME`: a path to the directory containing your private keys. Default to:
    * `~/.ssh` for a [file key store](#key-store)
    * `ssh` for the [pass key store](#key-store)
* `SSH_X_LIFE`: the default lifetime of the loaded keys (Default to `15m`)
* `SSH_X_AGENT_ENV`: The location of the agent env file. Default to `/home/admin/.ssh/ssh-x-agent.env`

## Key Store

We support 2 keys store:
* `file`: where the key is stored in a file system directory (default)
* `pass`: where the key is stored in the [pass password manager](https://www.passwordstore.org/)

You choose your key store with the variable [SSHX_KEY_STORE](#ssh-x)

For the 2 stores, the key is located at `/KeyName` where :
* [SSHX_KEY_HOME](#ssh-x) defines the directory location.
* `KeyName` is the name of the private key (ie the public key without the `.pub` extension)