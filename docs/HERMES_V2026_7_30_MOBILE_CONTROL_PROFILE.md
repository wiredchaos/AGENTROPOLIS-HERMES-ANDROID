# Hermes v2026.7.30 Mobile Control Profile

**Runtime baseline:** `NousResearch/hermes-agent@v2026.7.30`  
**Mobile surface:** AGENTROPOLIS HERMES Android  
**Status:** CANARY

## Mobile role

The Android application is a control and observation surface. It must not become an unbounded authority source merely because it can reach a Hermes runtime.

## Required controls

- explicit runtime and backend identity display;
- authenticated session binding;
- read-only default for newly connected runtimes;
- separate approvals for tool writes and consequential actions;
- visible capability scope and lease expiry;
- gateway, queue, session, and subagent health;
- stop, revoke, quarantine, and human-handoff controls;
- receipt access for mobile-triggered actions;
- secure handling of notifications, attachments, and deep links.

## Canary tests

- connect and reconnect to local and remote lanes;
- reject backend identity mismatch;
- resume session without replaying a command;
- cancel queued work;
- revoke a delegated capability;
- approve and deny a policy-gated action;
- receive completion and incident notifications;
- verify receipt correlation;
- recover after gateway restart.

Mobile convenience may not weaken the same authority and evidence rules enforced by desktop Mission Control.