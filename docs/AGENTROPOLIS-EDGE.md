# AGENTROPOLIS EDGE v0.1 — Pocket Grid

## Purpose

AGENTROPOLIS EDGE is the mobile and low-power execution profile for the AGENTROPOLIS Intelligence Grid.

The phone controls the city without being required to run the city.

HERMES EDGE remains the operator surface. Heavy inference, large model residency, background agent swarms, and expensive spatial rendering are routed away from the phone unless a verified device profile explicitly permits them.

## Canonical placement

```text
AGENTROPOLIS Intelligence Grid
        |
      HERMES
        |
  Execution Router
        |
  AGENTROPOLIS EDGE
        |
    HERMES EDGE
        |
      Mobile
```

AGENTROPOLIS EDGE is Layer 1 infrastructure. It is not a district and not a separate application ecosystem.

ASBE is not part of this base stack. ASBE remains AGENTIC STUDIOS BACK END, scoped to Agentic Studios in the Entertainment District.

## Components

- HERMES EDGE — mobile mission-control surface
- Mobile Resource Governor — thermal, battery, memory, foreground/background, network and render pressure
- Mobile Spatial Renderer — reduced mobile spatial profile that preserves the 3D city metaphor without requiring desktop-class rendering
- Mobile Context Compiler — generates minimum-necessary task and memory packages for the device
- Hardware Capability Profiler — measures device capabilities and classifies safe execution lanes
- Quantization Torque — selects minimum necessary cognition/context budget; never determines truth
- Execution Router — selects local, BYOC, workstation, grid, or external-provider execution
- EDGE-MOE Adapter — optional oversized MoE local runtime lane based on BigMoeOnEdge-style expert streaming
- Utility Meter — accounts for CPU, memory, storage I/O, network and remote compute usage
- WikiVault receipts — evidence and provenance authority for execution receipts
- 54-T — containment and effective-capability verification authority

## Operating modes

### POCKET
Default mobile mode.

The phone handles UI, voice, authentication, encrypted local state, task dispatch, lightweight deterministic operations, compact memory context and result streaming.

Heavy model inference and heavyweight spatial workloads are remote by default.

### EDGE
Permits verified lightweight local inference, embeddings, classification, summarization, limited local RAG, and bounded local agents when device state is healthy.

### POWER
Explicit opt-in for sustained local compute on verified hardware. EDGE-MOE and larger local GGUF execution may be enabled only when the Resource Governor and 54-T both permit the lane.

## Thermal state machine

```text
NOMINAL
  -> normal profile

WARMING
  -> reduce frame budget
  -> reduce spatial detail
  -> prohibit heavyweight local model startup
  -> prefer remote execution

SERIOUS
  -> terminate or checkpoint local inference
  -> remote execution only for heavy tasks
  -> suspend background animation and nonessential agents
  -> simplify spatial scene

CRITICAL
  -> thin HERMES UI only
  -> preserve state and receipts
  -> disable local heavyweight compute
```

Thermal policy is default-deny for escalation. No thermal event may grant additional capability.

## Execution routing contract

The router evaluates:

1. user intent
2. task capability requirement
3. Quantization Torque cognition budget
4. hardware profile
5. thermal state
6. battery state
7. memory pressure
8. network quality
9. execution cost/budget
10. 54-T effective capability state

The preferred route is the least-expensive verified execution surface capable of completing the task without degrading the user device.

Supported targets include:

- deterministic local execution
- lightweight local model
- standard local llama.cpp/GGUF runtime
- EDGE-MOE streamed MoE runtime
- user workstation or server
- user cloud account
- AGENTROPOLIS grid worker
- approved external provider

## EDGE-MOE doctrine

EDGE-MOE is not the default mobile runtime.

It is a memory-pressure escape hatch for verified Mixture-of-Experts models. Streaming experts from storage can reduce resident RAM requirements, but it does not remove CPU, storage I/O, battery, or thermal cost.

Therefore:

- disabled in POCKET mode
- conditionally available in EDGE mode only for approved low-impact profiles
- available in POWER mode only with explicit resource and 54-T approval
- immediately checkpointed or stopped when thermal state crosses policy thresholds

Community benchmark claims are evidence candidates, not AGENTROPOLIS production guarantees.

## Mobile spatial profile

Desktop-class spatial rendering must compile to a bounded mobile profile:

- capped frame rate
- dynamic resolution / device-pixel-ratio ceiling
- level-of-detail assets
- reduced particle counts
- reduced shadows and blur
- no permanent offscreen animation loops
- lazy-loaded districts and scenes
- sleeping offscreen agents
- event-driven UI updates over continuous polling where possible
- pause rendering when document/app is hidden or frozen
- respect reduced-motion preference

The mobile renderer preserves the canonical 3D city and Swarm Room experience while reducing local execution cost.

## Minimum-necessary mobile context

The phone should receive only the compact task context it needs:

```text
WikiVault full evidence
    -> governed retrieval
    -> gBRAIN derived index
    -> Quantization Torque
    -> Mobile Context Compiler
    -> HERMES EDGE context pack
```

A mobile context pack may contain:

- active mission
- current entities
- recent approved decisions
- relevant Memory Object IDs
- compact evidence references
- current UI/spatial state
- execution and policy receipts

The device should not mirror the entire Intelligence Grid.

## Required execution receipt

Every expensive routed operation must emit a receipt with at least:

- mission/task ID
- selected mode: POCKET | EDGE | POWER
- execution target
- model/runtime identifier when applicable
- hardware snapshot
- thermal state
- battery state
- memory pressure
- local vs remote decision
- fallback reason
- effective capability decision from 54-T
- utility usage counters
- benchmark/approval state
- timestamps

## Beta invariants

1. Heavyweight local inference is disabled by default on phones.
2. Mobile spatial rendering has bounded frame and particle budgets.
3. Hidden/offscreen visual loops must stop or throttle.
4. HERMES can route work to BYOC or grid compute without losing mission continuity.
5. Resource pressure can only reduce local authority, never expand it.
6. ASBE is never used as a general runtime, security, benchmark, or governance authority.
