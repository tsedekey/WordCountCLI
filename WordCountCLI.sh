#!/bin/bash

if [ "$#" -lt 1 ]; then
    echo "Usage: $0 <file_path> [additional arguments]"
    exit 1
fi

file_path=$1
shift

# Construct the command to run your Java application
command="java -jar ./target/WordCountCLI-1.0-SNAPSHOT-jar-with-dependencies.jar $file_path $@"

# Run the Java application using eval to handle arguments correctly
eval $command
