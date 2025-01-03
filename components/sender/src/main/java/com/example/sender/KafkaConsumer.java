package com.example.sender;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {
    // Usamos un patrón que coincida con los tópicos generados
    @KafkaListener(topics = "personalizado-salida", groupId = "myGroup")
    public void listen(@Payload String message) {
        // Aquí procesas el mensaje
        System.out.println("Mensaje recibido: " + message);
    }
}