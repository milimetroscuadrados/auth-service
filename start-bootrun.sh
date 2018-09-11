#!/usr/bin/env bash
./gradlew bootRun -PjvmArgs="-Xmx350m -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005"
