#!/bin/sh
app_path=$0
while [ -h "$app_path" ]; do
    ls=$(ls -ld "$app_path")
    link=${ls#*' -> '}
    case $link in /*)app_path=$link;;*)app_path=$(dirname "$app_path")/$link;;esac
done
APP_HOME=$(cd "$(dirname "$app_path")" && pwd -P)
APP_NAME="Gradle"
APP_BASE_NAME=$(basename "$0")
DEFAULT_JVM_OPTS='"-Xmx64m" "-Xms64m"'
CLASSPATH=$APP_HOME/gradle/wrapper/gradle-wrapper.jar
if [ -n "$JAVA_HOME" ]; then
    JAVACMD=$JAVA_HOME/bin/java
    [ ! -x "$JAVACMD" ] && echo "ERROR: JAVA_HOME invalid: $JAVA_HOME" && exit 1
else
    JAVACMD=java
    which java >/dev/null 2>&1 || (echo "ERROR: JAVA_HOME not set and java not in PATH" && exit 1)
fi
eval set -- $DEFAULT_JVM_OPTS $JAVA_OPTS $GRADLE_OPTS "\"-Dorg.gradle.appname=$APP_BASE_NAME\"" -classpath "\"$CLASSPATH\"" org.gradle.wrapper.GradleWrapperMain "$@"
exec "$JAVACMD" "$@"
