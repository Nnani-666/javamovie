#!/bin/bash
# run.sh – run the Movie Ticket Booking System

LIB_DIR="$(dirname "$0")/lib"
JAR="$LIB_DIR/sqlite-jdbc-3.42.0.0.jar"
SLF4J_API="$LIB_DIR/slf4j-api-1.7.36.jar"
SLF4J_SIMPLE="$LIB_DIR/slf4j-simple-1.7.36.jar"

CP="$(dirname "$0")/src/main/java:$JAR:$SLF4J_API:$SLF4J_SIMPLE"

JAVA_CMD="java"
if [ -f "$(dirname "$0")/jdk-17.0.8+7/Contents/Home/bin/java" ]; then
  JAVA_CMD="$(dirname "$0")/jdk-17.0.8+7/Contents/Home/bin/java"
fi

"$JAVA_CMD" -classpath "$CP" com.example.moviebooking.ui.HomeFrame
