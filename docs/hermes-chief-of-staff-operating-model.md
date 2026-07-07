# Hermes Chief of Staff Operating Model

Status: architecture note
Scope: AGENTROPOLIS HERMES ANDROID

## Core lock

The Android lane exists so Hermes can be supervised from a phone without turning the system into blind autonomy.

Mobile control should show status, approvals, and safe restart options.

## Mobile role

The Android experience should support:

- workstream status
- approval queue review
- agent run summaries
- GitHub issue links
- memory notes
- remote backend health checks
- safe command shortcuts

## Operating doctrine

1. Phone is supervision first.
2. Desktop or backend does the heavy work.
3. Mobile shows what changed and what needs approval.
4. High impact actions stay gated.
5. Every completed action should leave a short report.

## AGENTROPOLIS mapping

| System concept | Android role |
| --- | --- |
| Hermes | Chief of staff |
| Android app | Pocket command surface |
| GitHub | Execution record |
| Memory docs | Decision history |
| Backend | Worker runtime |
| Approval queue | Human review layer |

## Mobile views

### Today

- active workstreams
- blocked tasks
- pending approvals
- recent repo changes

### Agents

- Builder
- Media
- Knowledge
- Finance
- Arena
- Voice

### Approvals

- publish approval
- repo change approval
- artifact approval
- deployment approval

### Reports

- daily summary
- completed tasks
- failed tasks
- next actions

## Anti drift note

The phone should not become the whole operating system.

It is the remote control.

Hermes is the operator.

GitHub is the spine.

NEURO remains the final approval layer.