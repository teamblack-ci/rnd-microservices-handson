#!/usr/bin/env bash

echo "--> stopping previously running containers"
docker-compose kill

echo "--> removing stopped containers"
docker-compose rm -fv

echo "--> building microservice"
./gradlew clean bootRepackage

echo "--> building new containers"
docker-compose build --no-cache

echo "--> starting new containers"
docker-compose up -d
docker-compose ps
