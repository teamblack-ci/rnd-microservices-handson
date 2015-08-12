#!/usr/bin/env bash

echo "--> stopping previously running containers"
./rabbitmq.sh kill

echo "--> removing stopped containers"
./rabbitmq.sh rm -fv

echo "--> building new containers"
./rabbitmq.sh build --no-cache

echo "--> starting new containers"
./rabbitmq.sh up -d
./rabbitmq.sh ps
