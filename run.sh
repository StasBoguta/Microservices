#!/bin/sh

echo "********************************************************"
echo "Starting backend app"
echo "********************************************************"

java -Dserver.port=$SERVER_PORT \
     -jar /usr/local/target/backend.jar