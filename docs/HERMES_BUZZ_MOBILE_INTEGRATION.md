# Hermes Agent + Buzz Mobile Integration

The Android console becomes the mobile Mission Control surface for Buzz-coordinated Hermes Agent work.

```text
Buzz notification or task thread
  -> Android operator review
  -> inspect mandate, agent, capabilities, artifacts, and receipts
  -> approve, reject, pause, or revoke
  -> Hermes dispatcher
  -> bounded Hermes execution
  -> progress and receipt returned to Buzz
```

## Mobile surfaces

- community and channel selector
- signed task thread view
- agent identity and presence
- job state: queued, claimed, planning, executing, blocked, review ready, accepted, rejected
- requested and denied capabilities
- artifact and receipt browser
- approval queue for publishing, deployment, repository writes, payments, wallet actions, and destructive operations
- emergency pause, credential revoke, and agent disconnect

## Trust requirements

- relay URL defines the active Buzz community
- no job, memory, credential, or artifact crosses communities automatically
- approval screens show the exact requested side effect
- approval records include operator identity, request event, timestamp, and scope
- mobile notifications never substitute for opening and reviewing the full mandate
- secrets remain in platform-secure storage and are never written into Buzz messages
- receipt hashes are verified before an artifact is marked accepted

## First implementation slice

1. Read task status and signed thread metadata from one Buzz community.
2. Display Hermes job envelopes and artifact paths.
3. Support approve or reject for one guarded action class.
4. Return the signed decision to the originating thread.
5. Add pause and revoke controls.
6. Surface final receipts and external side effects.

**The phone is the approval surface, not the unrestricted execution host.**