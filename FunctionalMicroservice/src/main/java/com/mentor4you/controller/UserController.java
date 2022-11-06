package com.mentor4you.controller;

import com.mentor4you.model.User;
import com.mentor4you.repository.UserRepository;
import com.mentor4you.service.EmailService;
import com.mentor4you.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;

import java.util.List;


@RestController
@RequestMapping("/api/users")
public class UserController {

    UserService userService;
    UserRepository userRepository;
    EmailService emailService;


    @Autowired
    public UserController(UserService userService, UserRepository userRepository, EmailService emailService) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    //select all accounts
    @GetMapping
    List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/userByRequest{request}")
    User getUserByRequest(@PathVariable HttpServletRequest request) {
        return userService.getUserByRequest(request);
    }


    @PostMapping("/userById{id}")
    User getUserByRequest(@PathVariable Integer id) {
        return userService.getUserById(id);
    }

}
