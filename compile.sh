#!/bin/bash
# compile.sh – compile the Movie Ticket Booking System source files

LIB_DIR="$(dirname "$0")/lib"
mkdir -p "$LIB_DIR"
JAR="$LIB_DIR/sqlite-jdbc-3.42.0.0.jar"
SLF4J_API="$LIB_DIR/slf4j-api-1.7.36.jar"
SLF4J_SIMPLE="$LIB_DIR/slf4j-simple-1.7.36.jar"

if [ ! -f "$JAR" ]; then
  echo "Downloading SQLite JDBC driver..."
  curl -L -s -o "$JAR" https://repo1.maven.org/maven2/org/xerial/sqlite-jdbc/3.42.0.0/sqlite-jdbc-3.42.0.0.jar
fi
if [ ! -f "$SLF4J_API" ]; then
  echo "Downloading SLF4J API..."
  curl -L -s -o "$SLF4J_API" https://repo1.maven.org/maven2/org/slf4j/slf4j-api/1.7.36/slf4j-api-1.7.36.jar
fi
if [ ! -f "$SLF4J_SIMPLE" ]; then
  echo "Downloading SLF4J Simple..."
  curl -L -s -o "$SLF4J_SIMPLE" https://repo1.maven.org/maven2/org/slf4j/slf4j-simple/1.7.36/slf4j-simple-1.7.36.jar
fi

CP="$JAR:$SLF4J_API:$SLF4J_SIMPLE"

JAVAC_CMD="javac"
if [ -f "$(dirname "$0")/jdk-17.0.8+7/Contents/Home/bin/javac" ]; then
  JAVAC_CMD="$(dirname "$0")/jdk-17.0.8+7/Contents/Home/bin/javac"
fi

SRC_DIR="$(dirname "$0")/src/main/java"
find "$SRC_DIR" -name "*.java" > sources.txt
"$JAVAC_CMD" -classpath "$CP" @sources.txt
if [ $? -eq 0 ]; then
  echo "Compilation successful."
else
  echo "Compilation failed."
fi
