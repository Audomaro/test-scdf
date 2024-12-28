#!/bin/sh
echo "Descargando imagen de RabbitMQ..."

podman image pull rabbitmq:3.13.7-management

echo "Imagen descargada."

echo "Iniciando RabbitMQ..."

podman run -d \
    --name rabbitmq \
    -p 15672:15672 \
    -p 5672:5672 \
    -e RABBITMQ_DEFAULT_USER=myuser \
    -e RABBITMQ_DEFAULT_PASS=mypassword \
    rabbitmq:3.13.7-management &&
    start http://localhost:15672

echo "RabbitMQ iniciado."

echo "Esperando a que RabbitMQ esté disponible..."

while ! curl -s http://localhost:15672 >/dev/null; do
    sleep 2
done

start http://localhost:15672
