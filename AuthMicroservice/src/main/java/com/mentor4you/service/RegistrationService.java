package com.mentor4you.service;

import com.mentor4you.exception.RegistrationException;
import com.mentor4you.model.*;
import com.mentor4you.model.DTO.UserDTO;
import com.mentor4you.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
public class RegistrationService{

    @Autowired
    EmailService emailService;
    PasswordService passwordService;
    @Autowired
    UserRepository userRepository;

    public RegistrationService(EmailService emailService, PasswordService passwordService,UserRepository userRepository) {
        this.emailService = emailService;
        this.passwordService = passwordService;
        this.userRepository = userRepository;
    }

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

        return "User created";
    }

}
