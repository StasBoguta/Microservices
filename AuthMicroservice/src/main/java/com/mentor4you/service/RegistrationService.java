package com.mentor4you.service;

import com.mentor4you.config.ActiveMQProperties;
import com.mentor4you.event.CreateUserEvent;
import com.mentor4you.event.EventType;
import com.mentor4you.event.UpdateUserEvent;
import com.mentor4you.exception.RegistrationException;
import com.mentor4you.model.*;
import com.mentor4you.model.DTO.UserDTO;
import com.mentor4you.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class RegistrationService{

    private final EmailService emailService;
    private final PasswordService passwordService;
    private final UserRepository userRepository;
    private final JmsTemplate jmsTemplate;


    public String registration(UserDTO userDTO) throws RegistrationException{

        //checking email user is existing in database
        String email = userDTO.getEmail();
        if(emailService.emailExist(email)){
            throw new RegistrationException("User with email = "+ email + " already exist");
        }

        User user = new User();
        user.setEmail(email);
        user.setRole(Role.MENTEE);

        //checking user password is valid
        String password = userDTO.getPassword();
        if(!passwordService.isValidPassword(password)){
            throw new RegistrationException("Password is not valid");
        }

        //encode password
        user.setPassword(passwordService.encodePassword(userDTO.getPassword()));

        userRepository.save(user);

        CreateUserEvent createUserEvent = CreateUserEvent.builder()
                .id(user.getId())
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
        jmsTemplate.convertAndSend(ActiveMQProperties.USER_EVENTS_TOPIC,
                createUserEvent,
                message -> {
                    message.setStringProperty("EVENT_TYPE", EventType.USER_CREATE.getType());
                    return message;
                });

        return "User created";
    }
}
