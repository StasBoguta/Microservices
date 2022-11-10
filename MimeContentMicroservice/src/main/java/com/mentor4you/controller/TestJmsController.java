package com.mentor4you.controller;

import com.mentor4you.config.ActiveMQProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
@Slf4j
public class TestJmsController {

    private final JmsTemplate queueJmsTemplate;
    private final JmsTemplate topicJmsTemplate;
    private static final String MESSAGE = "Hello, this is message number %s";
    public static int messageId = 0;

    @GetMapping
    public ResponseEntity<?> sendTestMessage() {
        log.info("Sending message with id={}", messageId);

        String message = MESSAGE.formatted(messageId++);
        queueJmsTemplate.convertAndSend(ActiveMQProperties.QUEUE_NAME, message);
        topicJmsTemplate.convertAndSend(ActiveMQProperties.TOPIC_NAME, message);

        return ResponseEntity.ok().build();
    }
}
