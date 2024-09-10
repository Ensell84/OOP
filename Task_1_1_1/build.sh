#!/bin/bash

SRC_DIR="src/main/java"
BIN_DIR="bin"
DOC_DIR="doc"

# Directories for compilation and documentation:
mkdir $BIN_DIR
mkdir $DOC_DIR

# Compiling .java files to .class files and put to BIN_DIR
javac -d $BIN_DIR $(find $SRC_DIR -name "*.java")

# Generating documentation:
javadoc -d $DOC_DIR -sourcepath $SRC_DIR -subpackages ru.nsu.bondar

# Executing Application:
java -cp $BIN_DIR ru.nsu.bondar.Main
