#!/bin/bash


CMD="java -jar"
BASE_DIR=`dirname $0`
JAR="${BASE_DIR}/../../../target/Smart_fridge-1.0-SNAPSHOT.jar"

${CMD} ${JAR} 1  ##$@




