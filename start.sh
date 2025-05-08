#!/bin/bash

mvn -Dmaven.test.skip package
docker network create subs-network
docker compose up -d

