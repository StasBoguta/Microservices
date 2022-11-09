package com.mentor4you.controller;

import com.mentor4you.config.RabbitConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
@Slf4j
public class TestJmsController {

    private final RabbitTemplate rabbitTemplate;
    private static final String MESSAGE = "Hello, this is message number %s";
    public static int messageId = 0;

    @GetMapping
    public ResponseEntity<?> sendTestMessage() {
        log.info("Sending message with id={}", messageId);
        rabbitTemplate.convertAndSend(RabbitConfig.QUEUE_NAME, MESSAGE.formatted(messageId++));
        return ResponseEntity.ok().build();
    }
}
