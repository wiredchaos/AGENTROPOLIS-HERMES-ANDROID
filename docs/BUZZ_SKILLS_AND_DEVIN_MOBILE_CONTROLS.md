# Buzz Skills and Devin Mobile Controls

The Android console should expose the operational state of three Buzz execution routes:

- native remote Hermes gateway
- shared-profile Hermes ACP
- built-in ACP presets, including Devin

## Mobile status surface

For native Hermes gateway jobs, show relay connection, agent identity, allowlist mode, mention requirement, watched channels, last inbound event, last outbound accepted event, and gateway health.

For media delivery, show source filename, transformed filename when applicable, media hash, target channel, accepted status, and Buzz event ID.

For Devin ACP jobs, show selected repository or workspace, requested capabilities, branch or worktree, files changed, tests run, artifact references, and whether merge or deployment approval is pending.

## Mobile approvals

The Android console may approve or reject explicitly presented actions such as:

- publish media externally
- merge a reviewed branch
- deploy an approved build
- broaden a channel allowlist
- enable community-wide invocation
- install build tools or persistence services

Approval must be signed, scoped, time-bounded where possible, and linked to the originating Buzz thread and job receipt.

## Emergency controls

The operator surface should provide:

- pause agent
- revoke agent credential
- disable a runtime lane
- reject pending external actions
- view the last accepted Buzz event ID
- inspect recent receipts without revealing secret values

Installing a skill or discovering the Devin preset does not grant authority. The mobile console remains an approval and revocation surface, not a substitute for runtime enforcement.
