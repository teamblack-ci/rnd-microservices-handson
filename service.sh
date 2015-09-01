#!/usr/bin/env bash

echo "--> stopping previously running containers"
docker-compose kill

echo "--> removing stopped containers"
docker-compose rm -fv

echo "--> building microservice"
cd catalog
./gradlew clean bootRepackage
cd ..

cd order
./gradlew clean bootRepackage
cd ..
echo "--> building new containers"
docker-compose build --no-cache

echo "--> starting new containers"
docker-compose up -d
docker-compose ps
