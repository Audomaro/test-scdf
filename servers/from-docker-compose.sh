#!bin/sh
curl https://raw.githubusercontent.com/spring-cloud/spring-cloud-dataflow/main/src/docker-compose/docker-compose.yml -o docker-compose.yml
curl https://raw.githubusercontent.com/spring-cloud/spring-cloud-dataflow/main/src/docker-compose/docker-compose-rabbitmq.yml -o docker-compose-rabbitmq.yml
curl https://raw.githubusercontent.com/spring-cloud/spring-cloud-dataflow/main/src/docker-compose/docker-compose-postgres.yml -o docker-compose-postgres.yml

export DATAFLOW_VERSION=2.11.5
export SKIPPER_VERSION=2.11.5
docker-compose -f docker-compose.yml -f docker-compose-rabbitmq.yml -f docker-compose-postgres.yml up -d
