#!/usr/bin/env bash

echo "--> stopping previously running containers"
docker-compose kill

echo "--> removing stopped containers"
docker-compose rm -fv

echo "--> building microservice"
cd shared
./gradlew install
cd ..

cd catalog
./gradlew bootRepackage
cd ..

cd order
./gradlew bootRepackage
cd ..

cd bakery
./gradlew bootRepackage
cd ..

cd delivery
./gradlew bootRepackage
cd ..

cd order-ui
./gradlew bootRepackage
cd ..

echo "--> building new containers"
docker-compose build --no-cache

echo "--> starting new containers"
docker-compose up -d
docker-compose ps
