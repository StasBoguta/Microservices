package com.mentor4you.controller;

import com.mentor4you.model.DTO.LoginDTO;
import com.mentor4you.repository.MenteeRepository;
import com.mentor4you.repository.MentorRepository;
import com.mentor4you.repository.UserRepository;
import com.mentor4you.service.*;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.naming.AuthenticationException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class ResetPasswordController {

    @Autowired
    EmailService emailService;
    PasswordService passwordService;
    UserService userService;
    ResetPasswordService resetPasswordService;
    MentorRepository mentorRepository;
    UserRepository userRepository;
    MenteeRepository menteeRepository;
    AuthenticationService authenticationService;

    public ResetPasswordController(  ResetPasswordService resetPasswordService, AuthenticationService authenticationService,EmailService emailService, PasswordService passwordService, MentorRepository mentorRepository, UserRepository userRepository, MenteeRepository menteeRepository) {
        this.authenticationService = authenticationService;
        this.emailService = emailService;
        this.passwordService = passwordService;
        this.mentorRepository = mentorRepository;
        this.userRepository = userRepository;
        this.menteeRepository = menteeRepository;
        this.resetPasswordService= resetPasswordService;
    }

    @PostMapping("/resetPassword")
    ResponseEntity<?> resetPassword(@RequestBody LoginDTO dto) {
        Map<String, String> res = new HashMap<>();
        try {
            String token = authenticationService.login(dto);
            res.put("message","You have successfully logged in");
            res.put("token", token);
            return ResponseEntity.ok(res);

        }catch (AuthenticationException ex) {
            res.put("message", ex.getMessage());
            return ResponseEntity.badRequest().body(res);
        }

    }


    @Operation(summary = "send security test email")
    @GetMapping("/sendSecurityEmail/{sendTo}")
    public void sendSecurityEmail(@PathVariable(value = "sendTo") String sendTo) throws MessagingException {
        resetPasswordService.chekemail(sendTo);
    }

}
