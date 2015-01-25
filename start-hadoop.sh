#!/bin/bash

if [ -z "$HADOOP_PREFIX" ]; then
    export HADOOP_PREFIX=/usr/local/hadoop
fi

# Prepare HDFS
${HADOOP_PREFIX}/bin/hdfs namenode -format
${HADOOP_PREFIX}/sbin/start-dfs.sh

# Prepare Yarn
${HADOOP_PREFIX}/sbin/start-yarn.sh

# Copy File
${HADOOP_PREFIX}/bin/hdfs dfs -mkdir /input
if [ -d ../test_images ]; then
    ${HADOOP_PREFIX}/bin/hdfs dfs -put ../test_images/* /input
fi