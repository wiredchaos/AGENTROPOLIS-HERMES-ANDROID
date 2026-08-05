# Hermes Desktop Android Runtime Adapter

This adapter incorporates the verified community Android compatibility runtime from `Dadmin88/hermes-desktop-android` without copying its implementation into the AGENTROPOLIS authority surface.

## Boundary

- **Upstream runtime:** Termux + Termux:X11 + Ubuntu PRoot + source-built Hermes Desktop.
- **AGENTROPOLIS role:** mobile Mission Control, approval, revocation, receipt review, policy display, and runtime identity verification.
- **Not granted by installation:** unrestricted ADB, wallet access, production secrets, repository writes, deployment authority, or permanent capabilities.

## Install

From the Termux host shell:

```bash
bash runtime-adapters/hermes-desktop-android/install.sh
```

To verify the pinned release without installing:

```bash
bash runtime-adapters/hermes-desktop-android/install.sh --verify-only
```

The wrapper downloads the upstream release installer and checksum, verifies the checksum, and passes the pinned Hermes revision from `upstream.lock`.

## Governance defaults

1. New runtime connections are read-only.
2. Runtime identity must match the registered backend identity.
3. Consequential actions require an explicit, signed, time-bounded approval.
4. Wireless ADB remains disabled until a separate capability lease is approved.
5. Mobile-triggered actions must produce a correlated receipt.
6. Stop, revoke, quarantine, and human-handoff controls remain available.

## Upstream maintenance

Upstream releases are not followed automatically. Update `upstream.lock` only after reviewing the upstream diff, validating checksums, running adapter tests, testing on a canary device, and recording the approved runtime and Hermes revisions.
