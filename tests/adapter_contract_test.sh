#!/usr/bin/env bash
set -eu

repo_root=$(CDPATH= cd -- "$(dirname -- "$0")/.." && pwd)
lock="$repo_root/runtime-adapters/hermes-desktop-android/upstream.lock"
policy="$repo_root/policies/mobile-authority.yaml"
installer="$repo_root/runtime-adapters/hermes-desktop-android/install.sh"

fail() { printf 'FAIL: %s\n' "$1" >&2; exit 1; }

[ -f "$lock" ] || fail 'missing upstream lock'
[ -f "$policy" ] || fail 'missing mobile authority policy'
[ -f "$installer" ] || fail 'missing adapter installer'

grep -Eq '^UPSTREAM_RELEASE=v[0-9]+\.[0-9]+\.[0-9]+$' "$lock" || fail 'release is not pinned'
grep -Eq '^UPSTREAM_COMMIT=[0-9a-f]{40}$' "$lock" || fail 'upstream commit is not a full SHA'
grep -Eq '^HERMES_AGENT_COMMIT=[0-9a-f]{40}$' "$lock" || fail 'Hermes commit is not a full SHA'
grep -q 'default_mode: read_only' "$policy" || fail 'read-only default missing'
grep -q 'wallet_signing:' "$policy" || fail 'wallet policy missing'
grep -q 'session_entropy' "$policy" || fail 'thermodynamic observability missing'
bash -n "$installer"
printf 'adapter contract checks passed\n'
