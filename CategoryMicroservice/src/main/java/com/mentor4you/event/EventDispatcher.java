package com.mentor4you.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mentor4you.config.ActiveMQProperties;
import com.mentor4you.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class EventDispatcher {

    private final ObjectMapper objectMapper;

    private final CategoryRepository categoryRepository;


    @JmsListener(destination = ActiveMQProperties.USER_EVENTS_TOPIC,
            containerFactory = "userJmsListenerContainerFactory",
            selector = "EVENT_TYPE = 'USER_UPDATE'")
    public void handleUpdateUserEvent(String updateUserEventString) throws JsonProcessingException {
        UpdateUserEvent updateUserEvent = objectMapper.readValue(updateUserEventString, UpdateUserEvent.class);
        categoryRepository.updateAuthorEmail(updateUserEvent.getId(), updateUserEvent.getEmail());
    }
}
