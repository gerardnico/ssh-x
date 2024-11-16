% ssh-x-key(1) Version Latest | Manage your keys


# NAME

`ssh-x-key` - A command to manage your OpenSSH keys


## SYNOPSIS

```bash
ssh-x key command options
```

where `command` can be:

* `add`          - Add a private key from the agent. This is a relatif name (ie the name of the key not the path)
* `comment`      - Set or print the comment
* `ls`           - List all the keys, fingerprint and permissions on the file system and agent
* `passphrase`   - Set, delete a passphrase
* `perm`         - Set the correct permission for the keys (ie 0600)
* `rm`           - Remove a private key from the agent

Permissions should be set to `0600`.
We control also them for public key as `ssh` will refuse to use them as identity input otherwise.
(ie in this case, ssh will lookup a private key in the agent)

