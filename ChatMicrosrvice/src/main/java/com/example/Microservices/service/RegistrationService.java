package com.example.Microservices.service;

import com.example.Microservices.exeptions.RegistrationException;
import com.example.Microservices.model.*;
import com.example.Microservices.model.DTO.UserDTO;
import com.example.Microservices.repository.MenteeRepository;
import com.example.Microservices.repository.MentorRepository;
import com.example.Microservices.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final EmailService emailService;
    private final PasswordService passwordService;

    private final MentorRepository mentorRepository;
    private final UserRepository userRepository;
    private final MenteeRepository menteeRepository;

    public String registration(UserDTO userDTO) throws RegistrationException {

        //checking email user is existing in database
        String email = userDTO.getEmail();
        if(emailService.emailExist(email)){
            throw new RegistrationException("User with email = "+ email + " already exist");
        }

        User user = new User();
        Accounts accounts = new Accounts();

        user.setEmail(email);
        //checking user password is valid
        String password = userDTO.getPassword();
        if(!passwordService.isValidPassword(password)){
            throw new RegistrationException("Password is not valid");
        }

        //encode password
        user.setPassword(passwordService.encodePassword(userDTO.getPassword()));

        user.setRegistration_date(LocalDateTime.now());

        user.setStatus(true);

        user.setBan(false);

        accounts.setUser(user);

        //add role and create record
        if(userDTO.getRole().equals("mentor")){
            //role Mentor
            Mentors mentor = new Mentors();
            user.setRole(Role.MENTOR);
            mentor.setAccounts(accounts);
            mentorRepository.save(mentor);
        }else{
            Mentees mentee = new Mentees();
            user.setRole(Role.MENTEE);
            mentee.setAccounts(accounts);
            menteeRepository.save(mentee);
        }
        return "User created";
    }

}
