# Agent Runtime Position

This repo is part of the Agentropolis application surface layer.

The uploaded React files should be treated as a runtime/interface layer, not as the full Agentropolis operating system.

## Runtime trio

- `agentropolis-core.tsx` defines client-side agent types, events, bus behavior, brain adapters, and browser harness helpers.
- `agentropolis-provider.tsx` wraps React surfaces with city state, events, agents, FOMO state, and action hooks.
- `agentropolis-stage.tsx` renders the visible floating city overlay for TV, social, or city surfaces.

## Boundary

The runtime should consume data and contracts from the wider spine.

Source-of-truth responsibilities should stay with the proper layers:

- Canon and ontology define districts, roles, schemas, and relationships.
- Backend and MCP services own persistence, identity, event history, and coordination.
- District repos expose capabilities, skills, and data contracts.
- Application surfaces render and interact through the runtime.

## Build rule

Do not copy the full ecosystem into this repo. Connect this surface to the shared spine, then render the appropriate runtime experience.