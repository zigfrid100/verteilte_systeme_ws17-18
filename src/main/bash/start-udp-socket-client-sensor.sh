#!/bin/bash


CMD="java -jar"
BASE_DIR=`dirname $0`
JAR="${BASE_DIR}/../../../target/Smart_fridge-1.0-SNAPSHOT.jar"



for((i=1;i<=3;i++))
do
	${CMD} ${JAR} 2 & ##$@
done



