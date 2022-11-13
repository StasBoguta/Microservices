package com.mentor4you.service;

import com.mentor4you.config.ActiveMQProperties;
import com.mentor4you.event.EventType;
import com.mentor4you.event.UpdateUserEvent;
import com.mentor4you.model.*;
import com.mentor4you.repository.*;

import lombok.RequiredArgsConstructor;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final JmsTemplate jmsTemplate;

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public void updateUserEmail(Integer userId, String newEmail) {
        userRepository.findById(userId).ifPresent(user -> {
            user.setEmail(newEmail);
            userRepository.save(user);

            UpdateUserEvent updateUserEvent = UpdateUserEvent.builder()
                    .id(user.getId())
                    .email(user.getEmail())
                    .build();
            jmsTemplate.convertAndSend(ActiveMQProperties.USER_EVENTS_TOPIC,
                    updateUserEvent,
                    message -> {
                        message.setStringProperty("EVENT_TYPE", EventType.USER_UPDATE.getType());
                        return message;
                    });
        });
    }
}
