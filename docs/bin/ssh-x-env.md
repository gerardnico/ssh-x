% ssh-x-env(1) Version Latest | SSH-X Env


# NAME

`ssh-x-env` - Print the env in an export format


## SYNOPSIS

${SYNOPSIS}


## ENV


### SSH-X

* `SSH_X_KEY_STORE`: the name of the [key store](#key-store) that contains the private key. Possible values:
    * `file`: (default) for the file system
    * `pass`: for the [pass password manager](https://www.passwordstore.org/)
  * `SSH_X_KEY_HOME`: a path to the directory containing your private keys. Default to:
      * the absolute path `$HOME/.ssh`: for the [file key store](#key-store):  
      * the relative path `ssh-x` for the [pass key store](#key-store). This path:
        * relative to [PASSWORD_STORE_DIR](https://man.archlinux.org/man/extra/pass/pass.1.en#PASSWORD_STORE_DIR)
        * should not be start with a point. Otherwise, pass will not see it
* `SSH_X_LIFE`: the default lifetime of the loaded keys (Default to `15m`) Used if a time interval is not found in the `AddKeysToAgent` conf.
* `SSH_X_AGENT_ENV`: The location of the agent env file. Default to `$HOME/.ssh/ssh-x-agent.env`
* `SSH_X_KEY_PASSPHRASE_xxx`: The passphrase for a protected key to be loaded when the agent starts. See [ssh-x-agent-init](ssh-x-agent-init.md)
* `SSH_X_TIMEOUT`: the timeout to enter the passphrase. In non-interactive mode, `ssh-add` will freeze if the passphrase is not correct.

### SSH

This utility prints also [ssh env](https://man.openbsd.org/ssh.1#ENVIRONMENT)

* [SSH_AUTH_SOCK](https://man.openbsd.org/ssh.1#SSH_AUTH_SOCK): The location of the agent socket Default to `$HOME/.ssh/agent.sock`
* [SSH_ASKPASS](https://man.openbsd.org/ssh.1#SSH_ASKPASS) value is a path to an executable that emits the secret to stdout (known also as pinentry in pgp). `ssh-x` has 2:
  * [ssh-x-askpass-env](ssh-x-askpass-env.md) used to pass the password from a secret store
  * [ssh-x-askpass-prompt](ssh-x-askpass-prompt.md) used to prompt the user for the secret (in the terminal or guid, detected automatically)
* [SSH_ASKPASS_REQUIRE](https://man.openbsd.org/ssh.1#SSH_ASKPASS_REQUIRE) used to require `SSH_ASKPASS` behavior


## Key Store

We support 2 keys store:
* `file`: where the key is stored in a file system directory (default)
* `pass`: where the key is stored in the [pass password manager](https://www.passwordstore.org/)

You choose your key store with the variable [SSHX_KEY_STORE](#ssh-x)

For the 2 stores, the key is located at `$SSHX_KEY_HOME/KeyName` where :
* [SSHX_KEY_HOME](#ssh-x) defines the directory location.
* `KeyName` is the name of the private key (ie the public key without the `.pub` extension)