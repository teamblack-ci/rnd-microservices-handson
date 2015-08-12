#!/usr/bin/env bash

docker-compose -p eventbus -f rabbitmq.yml $@
