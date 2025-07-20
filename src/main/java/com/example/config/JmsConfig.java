package com.example.config;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import jakarta.jms.Queue;

@Configuration
public class JmsConfig {
    @Bean
    public Queue bookingNotificationQueue() {
        return new ActiveMQQueue("bookingNotificationQueue");
    }
} 