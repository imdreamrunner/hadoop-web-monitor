rm -R ./target/
echo "Start packaging..."
mvn package
echo "Done packaging, start running..."
cd target && java -jar web-monitor-1.0-SNAPSHOT-jar-with-dependencies.jar