#!/bin/bash

JAVA_OPTS="-Xmx512m -XX:MaxPermSize=128m -XX:ReservedCodeCacheSize=128m -cp sbt-launch-0.13.0.jar:jansi-1.11.jar"

exec java $JAVA_OPTS xsbt.boot.Boot "$@"
