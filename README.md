# AGENTROPOLIS HERMES

Public Hermes operator surfaces for the Agentropolis Intelligence Grid.

## Android console

The Android mobile console connects to a self-hosted Hermes WebUI for sessions, skills, memory, tasks, files, and operator approvals.

## SUPER HERMES capability grid

The `docs/` site is a public, dependency-free prototype that accepts a plain-language mission and returns:

- Ranked **skills, tools, and resources**.
- Cold, Warm, and Hot capability stratification.
- Explainable recommendation scores.
- A governed workflow with risk gates and checkpoints.
- Scheduled, conditional, and recurring automation proposals.
- Routing entropy, mission risk, and an exportable JSON receipt.
- A searchable capability index organized by district, trust, temperature, and risk.

### Architecture

```text
USER MANDATE
  -> MISSION DECOMPOSER
  -> STRATIFIED INDEX: SKILLS / TOOLS / RESOURCES
  -> AGENTROPOLIS DISTRICT ROUTER
  -> CAPABILITY RANKER
  -> AEGIS PROVENANCE + RISK GATE
  -> WORKFLOW COMPOSER
  -> AUTOMATION ARCHITECT
  -> BOUNDED EXECUTION
  -> RECEIPT LEDGER
  -> THERMODYNAMIC OBSERVER
```

### Temperature model

| Temperature | Meaning | Execution authority |
|---|---|---|
| Cold | Searchable catalog metadata | None |
| Warm | Retrieved, scanned, or evaluated cache | Review or sandbox only |
| Hot | Small mission-scoped capability set | Only within explicit authority |

### Governance

- Every Skill is a contract with the grid.
- Unknown provenance or licensing blocks publication.
- High-risk capabilities require explicit human approval.
- Credentials, persistent writes, financial actions, deletion, merging, and publication never inherit authority from a prompt.
- Every governed run should produce a receipt.
- Routing entropy, capability drift, retries, cost, latency, and useful-work ratio belong in the observability layer.

## Public site

GitHub Pages deployment is defined in `.github/workflows/pages.yml` and publishes the static site in `docs/` after changes land on `main`.

## Upstream relationship

Hermes Agent remains the upstream runtime: `NousResearch/hermes-agent`.

This repository currently contains the Agentropolis interfaces and recommendation layer. A dedicated public fork should remain mechanically close to upstream Hermes, while the SUPER HERMES control plane stays independently versioned to avoid merge conflicts and protect clean upstream synchronization.

## License

Original Agentropolis interface code is released under Apache-2.0 unless a file states otherwise. Hermes Agent and indexed third-party capabilities retain their original licenses.
