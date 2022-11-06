#!/bin/sh

echo "********************************************************"
echo "Starting backend app"
echo "********************************************************"

java -Dserver.port=$SERVER_PORT \
     -Dspring.profiles.active=$DB_TYPE \
      -jar /app/backend.jar