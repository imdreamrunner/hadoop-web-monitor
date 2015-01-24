#!/bin/bash
if [ -d ./target/ ];
then
    mvn clean
fi
echo "[INFO] Start packaging...  # bash"
mvn package -Dmaven.test.skip=true
if [ -f ./target/web-monitor-*-jar-with-dependencies.jar ];
then
    echo "[INFO] Done packaging, start running...  # bash"
    hdfs dfs -rm -R /output
    hadoop jar ./target/web-monitor-*-jar-with-dependencies.jar
fi
