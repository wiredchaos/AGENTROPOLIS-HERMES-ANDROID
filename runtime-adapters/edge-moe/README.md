# EDGE-MOE Runtime Adapter

EDGE-MOE is the AGENTROPOLIS adapter contract for oversized Mixture-of-Experts local inference where the full model cannot or should not be resident in device RAM.

Initial upstream reference: `Helldez/BigMoeOnEdge` (Apache-2.0). Upstream code is not vendored by this contract.

## Role

Expose a governed HERMES execution lane that can stream routed MoE experts from local storage while preserving AGENTROPOLIS resource, thermal, provenance, and 54-T controls.

## Activation triggers

Use this lane only when all are true:

- task requires a model capability available from an approved MoE model
- normal resident local inference fails or exceeds the verified memory budget
- AGENTROPOLIS EDGE mode is `EDGE` or `POWER`
- device profile explicitly permits sustained CPU and storage I/O
- thermal state is within the approved threshold
- 54-T verifies effective filesystem, process, model-file and runtime capabilities

Do not activate solely because a model technically fits on disk.

## Required environment

- approved llama.cpp-compatible runtime
- approved GGUF/MoE model profile
- local model path through a scoped capability handle
- Resource Governor metrics
- Utility Meter
- execution receipt sink

Raw credentials are never passed to the runtime.

## HERMES contract

HERMES requests a capability, not a specific implementation. The Execution Router may choose EDGE-MOE when policy permits it.

Input envelope:

```json
{
  "task_id": "...",
  "capability": "local_text_inference",
  "requested_model": "optional-model-id",
  "latency_class": "interactive|background",
  "quality_floor": "...",
  "locality_preference": "prefer_local|any",
  "mode": "EDGE|POWER"
}
```

The adapter returns model output plus an execution receipt. It must never claim completion without a runtime receipt.

## Runtime controls

The implementation should map approved profiles to bounded values such as:

- expert cache memory ceiling
- I/O concurrency
- dense-weight residency policy
- active expert count only when lossy operation is explicitly allowed
- overlap/prefetch features only when internally benchmarked
- maximum generation length
- cancellation/checkpoint behavior

## Thermal behavior

- `NOMINAL`: run within approved profile
- `WARMING`: finish short bounded operation or checkpoint; block new heavy jobs
- `SERIOUS`: checkpoint/cancel and reroute to remote compute
- `CRITICAL`: runtime disabled

The adapter may lose authority as device pressure increases. It may never gain authority because of resource pressure.

## Chains to

- Hardware Capability Profiler -> supplies hardware class
- Quantization Torque -> supplies cognition/model budget
- Mobile Resource Governor -> supplies current device pressure
- 54-T -> verifies effective capabilities
- Execution Router -> selects this adapter
- Utility Meter -> records local CPU/RAM/storage use
- WikiVault -> stores durable provenance and benchmark evidence

## Example

A user asks HERMES to run a private approved MoE locally. The resident runtime cannot satisfy the model's RAM requirement. The phone is in POWER mode, thermal state is NOMINAL, the hardware profile permits the configured storage/CPU budget, and 54-T passes the runtime capability set. The router selects EDGE-MOE and records why. If the device reaches SERIOUS thermal state, the job is checkpointed or cancelled and HERMES routes continuation to an approved remote worker.

## Production gate

No upstream or community performance claim constitutes an AGENTROPOLIS production profile. Every production profile requires an internally reproduced benchmark receipt tied to exact device, runtime, model, quantization, settings and software versions.
