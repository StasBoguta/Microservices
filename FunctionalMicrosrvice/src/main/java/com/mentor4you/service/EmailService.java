package com.mentor4you.service;

import com.mentor4you.model.DTO.EmailToModeratorRequest;
import com.mentor4you.model.User;
import com.mentor4you.repository.SystemEmailsRepository;
import com.mentor4you.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class EmailService {

    @Autowired
    UserRepository userRepository;
    JavaMailSender emailSender;
    SystemEmailsRepository systemEmailsRepository;

    public EmailService(UserRepository userRepository, JavaMailSender emailSender, SystemEmailsRepository systemEmailsRepository) {
        this.userRepository = userRepository;
        this.emailSender = emailSender;
        this.systemEmailsRepository = systemEmailsRepository;
    }

    //check user email is existing in database
    public boolean emailExist(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public boolean isEmailValidRegEx(String email) {
        // get emails name length it must be not more 129
        int length = email.length();

        // simple pattern check @  .
        Pattern pattern = Pattern.compile(".+@.+\\..+");
        Matcher matcher = pattern.matcher(email);

        return (matcher.matches() && length < 130);
    }

    public String updateEmail(String email, int id) {

        //TODO check email to valid with sending testEmail
        if (isEmailValidRegEx(email)) {

            if (userRepository.findByEmail(email).isEmpty()) {

                User userToUpdate = userRepository.findOneById(id);
                userToUpdate.setEmail(email);
                userRepository.save(userToUpdate);
                return "Email updated to " + userRepository.findOneById(id).getEmail();
            } else {
                return "email " + email + " is exist";
            }
        } else {
            return "Something wrong with thr email ->  " + email;
        }
    }


    public String sendEmailRandomCode(String sendTo, String messageText, String code) throws MessagingException {

        // Create a Simple MailMessage.
        //SimpleMailMessage message = new SimpleMailMessage();
        //message.setTo(sendTo);
        //message.setSubject("Test Simple Email");
        //message.setText("Hello, Im testing Simple Email");

        // Send Message!
        //this.emailSender.send(message)


        MimeMessage message = emailSender.createMimeMessage();
        boolean multipart = true;

        MimeMessageHelper helper = new MimeMessageHelper(message, multipart, "utf-8");

        String htmlMsg = "<h3>Im testing send a HTML email</h3>"
                + code;

        message.setContent(htmlMsg, "text/html");

        helper.setTo(sendTo);

        helper.setSubject("Test send HTML email");


        this.emailSender.send(message);

        return "Email Sent!";
    }
    public String resetPasswordmessage(String sendTo, String messageText, String code) throws MessagingException {

        MimeMessage message = emailSender.createMimeMessage();
        boolean multipart = true;
        MimeMessageHelper helper = new MimeMessageHelper(message, multipart, "utf-8");
        String htmlMsg = "<h3>You have a new password, do not tell anyone" +
                "</h3>" + code;

        message.setContent(htmlMsg, "text/html");

        helper.setTo(sendTo);

        helper.setSubject("Test send HTML email");


        this.emailSender.send(message);

        return "Email Sent!";
    }


    public ResponseEntity<String> sendEmailToModer(EmailToModeratorRequest request) throws MessagingException {

        String name = request.getName();
        String subject = request.getSubject();
        String messageText = request.getMessage();
        String emailRec = request.getEmailAdres();

        int id = 0;

        if (isEmailValidRegEx(emailRec)) {
            //find user by ID
            User user = userRepository.findUserByEmail(emailRec);
            if (user != null) {
                id = user.getId();
            }

            MimeMessage message = emailSender.createMimeMessage();
            boolean multipart = true;

            MimeMessageHelper helper = new MimeMessageHelper(message, multipart, "utf-8");

            //String htmlMsg = "<h3>" + messageText + "</h3>";

            String htmlMsg="";

            if (id != 0) {
                // String htmlMsg = "<h3 style=\"color: green\">" + messageText + "</color> </h3>";
                htmlMsg = "<h3>" + messageText + "<br><br>" + "from user with name " + name + " and Id " + id + "</h3>";

            } else {
                htmlMsg = "<h3>" + messageText + "<br><br>" + "from unauthorized with name " + name+ "</h3>";
            }
            message.setContent(htmlMsg, "text/html");
            helper.setTo(systemEmailsRepository.findEmailById(request.getEmailAdrId()));
            helper.setSubject(subject);
            this.emailSender.send(message);

            return new ResponseEntity<String>("Email Sent!", HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Email not valid", HttpStatus.CONFLICT);
        }
    }


    public void sendNotificationToEmail(String to, String text) throws MessagingException {

        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");

        String htmlMsg = "<h3> " + text + " </h3>";

        message.setContent(htmlMsg, "text/html");

        helper.setTo(to);

        helper.setSubject("Mentor4You team <3");

        this.emailSender.send(message);

    }

}
