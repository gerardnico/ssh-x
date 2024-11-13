% ssh-x-agent(1) Version Latest | SSH-X Agent Utilities


# NAME

`ssh-x-agent` - A cli of SSH Agent Utilities


# SYNOPSIS

${SYNOPSIS}

# SECURITY NOTE AND LIFETIME

`ssh-agent` enhances security by allowing you to use passphrase-protected SSH keys without
entering the passphrase every time.

However, anyone with access to the agent’s socket [SSH_AUTH_SOCK](ssh-x-env.md#ssh) and your user permissions can use the keys
managed by the agent.

That's why by default, we set a lifetime of 15m (3 times the default of 5m for sudo).

You can override it with the [SSH_X_LIFE environment](ssh-x-env.md)

Example:
```bash
# disable
export SSH_X_LIFE=0
# 1h
export SSH_X_LIFE=1h
```
