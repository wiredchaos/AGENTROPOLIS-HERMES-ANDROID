#!/usr/bin/env bash
set -eu

adapter_dir=$(CDPATH= cd -- "$(dirname -- "$0")" && pwd)
lock_file="$adapter_dir/upstream.lock"

if [ ! -f "$lock_file" ]; then
  printf 'Missing adapter lock: %s\n' "$lock_file" >&2
  exit 1
fi

# shellcheck disable=SC1090
. "$lock_file"

case "${UPSTREAM_RELEASE:-}" in
  v[0-9]*.[0-9]*.[0-9]*) ;;
  *) printf 'Invalid pinned upstream release.\n' >&2; exit 1 ;;
esac

case "${UPSTREAM_COMMIT:-}" in
  *[!A-Fa-f0-9]*|'') printf 'Invalid pinned upstream commit.\n' >&2; exit 1 ;;
esac

base="https://github.com/${UPSTREAM_REPOSITORY}/releases/download/${UPSTREAM_RELEASE}"
workdir=$(mktemp -d)
trap 'rm -rf "$workdir"' EXIT HUP INT TERM

curl -fSLo "$workdir/install-termux.sh" "$base/install-termux.sh"
curl -fSLo "$workdir/install-termux.sh.sha256" "$base/install-termux.sh.sha256"
(
  cd "$workdir"
  sha256sum --check install-termux.sh.sha256
)

printf 'Upstream adapter verified: %s@%s\n' "$UPSTREAM_REPOSITORY" "$UPSTREAM_RELEASE"
printf 'Hermes upstream pin: %s\n' "$HERMES_AGENT_COMMIT"

if [ "${1:-}" = "--verify-only" ]; then
  exit 0
fi

export HERMES_ANDROID_REPO_REF="$UPSTREAM_RELEASE"
export HERMES_AGENT_COMMIT
exec bash "$workdir/install-termux.sh"
