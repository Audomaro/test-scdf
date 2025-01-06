package com.example.my_procesor;

import org.springframework.context.annotation.Bean;

import com.fasterxml.jackson.databind.ObjectMapper;
// import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class Config {
    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public XmlMapper xmlMapper() {
        return new XmlMapper();
    }
}
