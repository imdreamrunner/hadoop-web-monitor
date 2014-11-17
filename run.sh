if [ ./target/ ];
then
    rm -R ./target/
fi
echo "Start packaging..."
mvn package
if [ ./target/web-monitor-*-jar-with-dependencies.jar ];
then
    echo "Done packaging, start running..."
    cd target && java -jar web-monitor-*-jar-with-dependencies.jar
fi