package com.mentor4you.rabbit;

import com.mentor4you.config.ActiveMQProperties;
import com.mentor4you.config.JmsConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MessageReceiver {

    @JmsListener(destination = ActiveMQProperties.QUEUE_NAME)
    public void receive(String message) {
        log.info("MessageReceiver received message: {}", message);
    }
}
