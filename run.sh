rm -R ./target/
echo "Start packaging..."
mvn package
echo "Done packaging, start running..."
cd target && java -jar web-monitor-*-jar-with-dependencies.jar