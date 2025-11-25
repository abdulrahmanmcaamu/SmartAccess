#!/usr/bin/env sh
set -e
DIR="$(cd "$(dirname "$0")"; pwd)"
CLASSPATH="$DIR/gradle/wrapper/gradle-wrapper.jar"
exec java -jar "$CLASSPATH" "$@"
