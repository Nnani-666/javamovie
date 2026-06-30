#!/bin/bash
# compile.sh – compile the Movie Ticket Booking System source files
# Place MySQL Connector/J JAR in ./lib directory (e.g., lib/mysql-connector-java-8.0.33.jar)

LIB_DIR="$(dirname "$0")/lib"
JAR="$LIB_DIR/mysql-connector-java-8.0.33.jar"

if [ ! -f "$JAR" ]; then
  echo "MySQL connector JAR not found at $JAR. Please download it first."
  exit 1
fi

SRC_DIR="$(dirname "$0")/src/main/java"
# Compile all .java files
find "$SRC_DIR" -name "*.java" > sources.txt
javac -classpath "$JAR" @sources.txt
if [ $? -eq 0 ]; then
  echo "Compilation successful."
else
  echo "Compilation failed."
fi
