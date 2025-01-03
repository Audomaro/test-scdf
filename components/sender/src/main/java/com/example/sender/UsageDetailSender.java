package com.example.sender;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.messaging.support.MessageBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Random;
import java.util.function.Supplier;

@Configuration()
@EnableScheduling
public class UsageDetailSender {

    private final String[] users = { "user1", "user2", "user3", "user4", "user5" };
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Random random = new Random();

    @Bean
    public Supplier<Message<String>> sendUsageDetail() {
        return () -> {
            try {
                UsageDetail usageDetail = generateUsageDetail();
                String json = objectMapper.writeValueAsString(usageDetail);
                System.out.println("Sending JSON message: " + json);
                return MessageBuilder.withPayload(json).build();
            } catch (Exception e) {
                throw new RuntimeException("Error generating JSON message", e);
            }
        };
    }

    @Scheduled(fixedRate = 1000)
    public void scheduleMessageSending() {
        sendUsageDetail().get();
    }

    private UsageDetail generateUsageDetail() {
        UsageDetail usageDetail = new UsageDetail();
        usageDetail.setUsername(users[random.nextInt(users.length)]);
        usageDetail.setDuration(random.nextInt(300));
        usageDetail.setData(random.nextInt(700));
        return usageDetail;
    }
}
