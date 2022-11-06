package com.mentor4you.service;


import com.mentor4you.model.*;
import com.mentor4you.repository.*;
import com.mentor4you.security.jwt.JwtProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;

import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;

import java.util.List;


@Service
public class UserService {

    @Autowired
    ApplicationEventPublisher applicationEventPublisher;
    PasswordService passwordService;
    UserRepository userRepository;
    JwtProvider jwtProvider;
    AuthenticationService authenticationService;
       AccountRepository accountRepository;

    public UserService(ApplicationEventPublisher applicationEventPublisher, PasswordService passwordService, UserRepository userRepository, JwtProvider jwtProvider, AuthenticationService authenticationService,  AccountRepository accountRepository) {
        this.applicationEventPublisher = applicationEventPublisher;
        this.passwordService = passwordService;
        this.userRepository = userRepository;
        this.jwtProvider = jwtProvider;
        this.authenticationService = authenticationService;

        this.accountRepository = accountRepository;
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }


    public User getUserByRequest(HttpServletRequest request){
        String token = jwtProvider.getTokenFromRequest(request);
        String email =jwtProvider.getLoginFromToken(token);
        return userRepository.findUserByEmail(email);
    }

    public User getUserById(Integer id){
        return userRepository.findOneById(id);
    }

}
