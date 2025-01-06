package com.example.my_procesor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.util.function.Function;

@Configuration
public class JsonToXmlProcessor {
    private final ObjectMapper objectMapper;
    private final XmlMapper xmlMapper;

    public JsonToXmlProcessor(ObjectMapper objectMapper, XmlMapper xmlMapper) {
        this.objectMapper = objectMapper;
        this.xmlMapper = xmlMapper;
    }

    @Bean
    public Function<Message<String>, Message<String>> process() {
        return input -> {
            try {
                // Convertir JSON a objeto
                UsageDetail usageDetail = objectMapper.readValue(input.getPayload(), UsageDetail.class);

                // Convertir objeto a XML
                String xml = xmlMapper.writeValueAsString(usageDetail);
                System.out.println("Converted to XML: " + xml);

                // Enviar mensaje XML
                return MessageBuilder.withPayload(xml)
                        .setHeader("contentType", "application/xml")
                        .build();
            } catch (Exception e) {
                // Manejar error de deserialización o conversión
                throw new RuntimeException("Error processing message", e);
            }
        };
    }
}