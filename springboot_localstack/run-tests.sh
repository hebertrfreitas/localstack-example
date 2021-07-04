#!/bin/sh
docker-compose down
docker-compose up -d localstack

echo "Aguardando localstack ficar disponível na porta 4566..."

while ! nc -z localhost 4566; do
  echo "Esperando..."
  sleep 2 # wait two seconds
done

./gradlew clean test