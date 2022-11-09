package com.mentor4you.rabbit;

import com.mentor4you.config.RabbitConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MessageReceiver {

    @RabbitListener(queues = RabbitConfig.QUEUE_NAME)
    public void receive(String message) {
        log.info("MessageReceiver received message: {}", message);
    }
}
