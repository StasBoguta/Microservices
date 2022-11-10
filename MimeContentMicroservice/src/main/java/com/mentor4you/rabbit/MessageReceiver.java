package com.mentor4you.rabbit;

import com.mentor4you.config.ActiveMQProperties;
import com.mentor4you.config.JmsConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MessageReceiver {

    @JmsListener(destination = ActiveMQProperties.QUEUE_NAME, containerFactory = "queueJmsListenerContainerFactory")
    public void receiveMessageFromQueue(String message) {
        log.info("Queue MessageReceiver received message: {}", message);
    }

    @JmsListener(destination = ActiveMQProperties.TOPIC_NAME, containerFactory = "topicJmsListenerContainerFactory")
    public void receiveMessageFromTopicFirst(String message) {
        log.info("Topic MessageReceiver 1 received message: {}", message);
    }

    @JmsListener(destination = ActiveMQProperties.TOPIC_NAME, containerFactory = "topicJmsListenerContainerFactory")
    public void receiveMessageFromTopicSecond(String message) {
        log.info("Topic MessageReceiver 2 received message: {}", message);
    }
}
