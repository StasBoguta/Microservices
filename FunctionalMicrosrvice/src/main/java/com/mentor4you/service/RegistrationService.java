package com.mentor4you.service;

import com.mentor4you.exception.RegistrationException;
import com.mentor4you.model.*;
import com.mentor4you.model.DTO.UserDTO;
import com.mentor4you.repository.MenteeRepository;
import com.mentor4you.repository.MentorRepository;
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
    MentorRepository mentorRepository;
    UserRepository userRepository;
    MenteeRepository menteeRepository;

    public RegistrationService(EmailService emailService, PasswordService passwordService, MentorRepository mentorRepository, UserRepository userRepository, MenteeRepository menteeRepository) {
        this.emailService = emailService;
        this.passwordService = passwordService;
        this.mentorRepository = mentorRepository;
        this.userRepository = userRepository;
        this.menteeRepository = menteeRepository;
    }

    public String registration(UserDTO userDTO) throws RegistrationException{

        //checking email user is existing in database
        String email = userDTO.getEmail();
        if(emailService.emailExist(email)){
            throw new RegistrationException("User with email = "+ email + " already exist");
        }

        User user = new User();
        Accounts accounts = new Accounts();

        user.setEmail(email);
        user.setAvatar("https://awss3mentor4you.s3.eu-west-3.amazonaws.com/avatars/standartUserAvatar.png");
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
