#!/bin/bash
echo "Iniciando spring-cloud"
java -jar spring-cloud-skipper-server-2.11.5.jar &
java -jar spring-cloud-dataflow-server-2.11.5.jar &
echo "Ctrl+C para detener los servidores..."
wait
