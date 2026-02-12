#!/bin/sh
set -e

clear
git clean -xdf
git submodule foreach git clean -xdf

export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64/
export JAVA_TOOL_OPTIONS="-XX:+UnlockExperimentalVMOptions -XX:+AlwaysPreTouch -XX:-TieredCompilation -XX:CICompilerCount=$(nproc) -XX:ReservedCodeCacheSize=2G -XX:+DisableAttachMechanism -XX:-DontCompileHugeMethods -XX:+ParallelRefProcEnabled -XX:+PerfDisableSharedMem -XX:+UseFPUForSpilling -XX:+UseStringDeduplication -XX:+UseTransparentHugePages -XX:+UseXMMForArrayCopy -XX:+UseFastUnorderedTimeStamps"
exec ./gradlew --no-daemon assembleRelease
