% ssh-x-agent(1) Version Latest | SSH-X Agent Utilities


# NAME

`ssh-x-agent` - A cli of SSH Agent Utilities


## SYNOPSIS

```bash
ssh-x-agent command options
```

where `command` can be:

* `init`  - Start one agent by computer (not one by session) and return env statement.
* `kill`  - Stop the agent process (given by `SSH_AGENT_PID` or any ssh-agent process).
* `state` - State of the agent

