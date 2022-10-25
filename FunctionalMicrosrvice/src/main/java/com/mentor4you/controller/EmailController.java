package com.mentor4you.controller;

import com.mentor4you.model.DTO.EmailToModeratorRequest;
import com.mentor4you.service.EmailService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;


@RestController
@RequestMapping("/api/emailToModerator")
public class EmailController {

    @Autowired
    EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @Operation(summary = "send email to moderator")
    @PostMapping("/sendEmailToModer")
    public ResponseEntity<String> sendEmailToModer(@RequestBody EmailToModeratorRequest request) throws MessagingException {

        return  emailService.sendEmailToModer(request);
    }
}

