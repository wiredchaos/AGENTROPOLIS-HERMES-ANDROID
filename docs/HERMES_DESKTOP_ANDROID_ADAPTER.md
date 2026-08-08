# Hermes Desktop Android Adapter Architecture

**Layer:** Application surface / supervised runtime adapter  
**Status:** CANARY  
**Upstream:** `Dadmin88/hermes-desktop-android@v0.1.1`

## Purpose

The adapter adds a local Android execution lane while preserving the rule that the phone is a supervision surface, not the authority root.

```text
NEURO / HUMAN AUTHORITY
          |
          v
AGENTROPOLIS HERMES ANDROID
Mission Control + Approval + Receipts
          |
          v
Mobile Capability Gateway
          |
          +-------------------+
          |                   |
          v                   v
Remote Hermes lanes     Local Android lane
Cloud / Claw workers    Termux + X11 + PRoot
          |                   |
          +---------+---------+
                    v
Identity -> Mandate -> Plan -> Execute -> Receipt -> Audit
```

## Security posture

The compatibility lane runs Electron with `--no-sandbox` inside a root PRoot guest. Android still confines Termux as an application, but Chromium defense-in-depth is reduced. The lane remains CANARY and must not receive broad credentials or wallet-signing authority.

Wireless ADB is a separate optional bridge. It is not enabled by the adapter installer and remains behind a short-lived explicit capability lease.

## Acceptance gates

- pinned upstream release and Hermes commit;
- checksum verification before install;
- backend identity displayed and verified;
- read-only first connection;
- no automatic ADB enablement;
- signed approval for writes, merges, publishing, and deployment;
- receipt correlation for every mobile-triggered action;
- stop, revoke, and quarantine tested;
- entropy, drift, thermal, battery, and memory telemetry visible;
- canary-device report attached before promotion.
