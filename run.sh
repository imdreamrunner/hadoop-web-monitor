if [ -d ./target/ ];
then
    rm -R ./target/
fi
echo "Start packaging..."
mvn package
if [ -f ./target/web-monitor-*-jar-with-dependencies.jar ];
then
    echo "Done packaging, start running..."
    hdfs dfs -rm -R /output
    java -jar ./target/web-monitor-*-jar-with-dependencies.jar
fi