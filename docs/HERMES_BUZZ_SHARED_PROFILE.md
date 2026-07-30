# Android Control Contract for Hermes Buzz Shared Profiles

AGENTROPOLIS-HERMES-ANDROID recognizes [`r0b0tlab/hermes-buzz-shared-profile`](https://github.com/r0b0tlab/hermes-buzz-shared-profile) as the desktop-side bridge that registers a Hermes profile as a Buzz managed agent.

## Desktop execution path

```text
Hermes profile
  -> hermes -p <profile> acp
  -> buzz-acp
  -> Buzz Desktop managed agent
```

The Android application does not rewrite `managed-agents.json` remotely and does not copy Hermes profile state. Its role is operator visibility and approval.

## Mobile surfaces

The Android console should display:

- profile and managed-agent name
- Buzz community and channel
- current ACP process state
- requested capability class
- artifact and receipt paths
- pending approval requirements
- latest bounded error
- pause, reject, revoke, and stop controls

## State invariant

The local Hermes profile remains the sole writable owner of configuration, memory, skills, sessions, `SOUL.md`, and `state.db`. Buzz stores the launch reference. Android observes and governs authorized operations without creating a third state owner.

## Security requirements

- never expose profile archives, private keys, `state.db`, or raw credentials in mobile notifications
- require re-authentication for approval of publishing, deployment, payment, credential, or destructive actions
- bind approval to the exact request event, capability set, artifact hash, and expiration
- show the originating Buzz community to prevent cross-community confusion
- fail closed when the desktop bridge or receipt cannot be verified

## Setup reference

```bash
hermes skills install amanning3390/hermes-buzz-shared-profile/hermes-buzz-shared-profile
python3 ${HERMES_SKILL_DIR}/scripts/shared_profile.py buzz-add --profile <profile>
```

Restart Buzz Desktop after registration.

## Honest boundary

The upstream skill provides profile registration and ACP launch. It does not provide remote mobile approval, capability sandboxing, signed receipts, or credential revocation. Those controls belong to the AGENTROPOLIS Android console, Mission Control, AEGIS, and the execution adapter.

## Upstream status

- Skill version reviewed: `0.3.0`
- Platforms: macOS, Linux, Windows 10/11
- License: MIT
