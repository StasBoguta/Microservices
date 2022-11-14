#!/bin/sh

echo "********************************************************"
echo "Starting backend app"
echo "********************************************************"

java -Dserver.port=$SERVER_PORT \
      -jar /app/backend.jar