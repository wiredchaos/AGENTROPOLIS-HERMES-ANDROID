# Hermes Blackbox Mobile Review

The Android console may display redacted Hermes Blackbox flight records and receipts for operator review. It must not convert a Blackbox v0.1 heuristic verdict into automatic completion.

## Mobile states

- `DRAFT` - captured but not assured
- `INCONCLUSIVE` - missing or ambiguous evidence
- `REJECTED` - failed integrity, policy, or evidence checks
- `VERIFIED` - AEGIS decision attached

## Required receipt card

Display:

- goal and session reference;
- capture time;
- event count and redaction count;
- event-stream hash and integrity state;
- claim verdicts and evidence gaps;
- artifact hashes when available;
- AEGIS decision identifier;
- operator approval requirement.

## Safety behavior

- Never render raw unredacted records.
- Hide absolute filesystem paths and machine identifiers.
- Require an explicit operator action for exports.
- Do not offer Approve for high-impact actions unless AEGIS verification is present.
- Mark heuristic `prove` output as advisory.
- Surface integrity mismatch and missing-assurance conditions prominently.

The mobile console is a review surface. It is not the authority root.
