package com.mentor4you.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String QUEUE_NAME = "defaultQueue";

    @Bean
    public Queue rabbitQueue() {
        return new Queue(QUEUE_NAME, false);
    }
}
