# AGENTROPOLIS-HERMES-ANDROID

Hermex Android beta mobile console for Hermes + Agentropolis. The app is privacy-first and self-hosted: it connects to your own `hermes-webui` server over secure HTTPS (for example via Tailscale or Cloudflare Tunnel), while Hermes runtime stays on your machine.

## Beta scope

- Server connection setup
- Auth token storage (encrypted on device)
- Live Hermes chat surface
- Session, profile, project, skills, memory/insight, task/cron, and approvals views
- Android file upload trigger
- Dark cyber-noir UI with explicit **BETA** labeling

## Build

```bash
./gradlew :app:assembleDebug
./gradlew :app:testDebugUnitTest
```

