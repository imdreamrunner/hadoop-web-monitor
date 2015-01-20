# Web Monitor

## Package the Project

Please build the project with Maven.

```bash
mvn package
```

However an run script is provided for fast testing, you can simply run it by `sh run.sh` or `./run.sh`.

## Dependencies

### Hadoop

The Hadoop package

### NanoHTTPD

Please download it [here](https://github.com/NanoHttpd/nanohttpd) and install it with Maven. However, you may wish to disable the testing process during building, which could require root privileges.

```bash
mvn install -DskipTests=true
```