if [ -d ./target/ ];
then
    mvn clean
fi
echo "Start packaging..."
mvn package
if [ -f ./target/web-monitor-*-jar-with-dependencies.jar ];
then
    echo "Done packaging, start running..."
    hdfs dfs -rm -R /output
    java -jar ./target/web-monitor-*-jar-with-dependencies.jar
fi