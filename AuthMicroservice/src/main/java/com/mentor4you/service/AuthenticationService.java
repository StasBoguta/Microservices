package com.mentor4you.service;

import com.mentor4you.exception.JwtAuthenticationException;
import com.mentor4you.model.DTO.LoginDTO;
import com.mentor4you.model.Role;
import com.mentor4you.model.User;
import com.mentor4you.repository.UserRepository;
import com.mentor4you.security.jwt.CustomUserDetails;
import com.mentor4you.security.jwt.JwtProvider;
import com.mentor4you.security.jwt.cache.event.OnUserLogoutSuccessEvent;
import io.jsonwebtoken.ExpiredJwtException;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.Date;


@Service
public class AuthenticationService {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;
    JwtProvider jwtProvider;

    int activeMentors = 0;
    int activeMentees = 0;

    public AuthenticationService(UserRepository userRepository, JwtProvider jwtProvider) {
        this.userRepository = userRepository;
        this.jwtProvider = jwtProvider;
    }


    public String login(LoginDTO request) throws AuthenticationException {
        User user = userRepository.findUserByEmail(request.getEmail());

        if(user==null){
            throw new AuthenticationException("Email is incorrect");
        }

        if(new BCryptPasswordEncoder().matches(request.getPassword(),user.getPassword())){
           Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    request.getEmail(),
                    request.getPassword()
            ));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            if (user.getRole().equals(Role.MENTOR))
                activeMentors++;

            if (user.getRole().equals(Role.MENTEE))
                activeMentees++;

            return jwtProvider.generateAuthToken(authentication);
        }

        throw new AuthenticationException("Password is incorrect");
    }

    public String logout(HttpServletRequest request, CustomUserDetails user) {
        String token = jwtProvider.getTokenFromRequest(request);
        OnUserLogoutSuccessEvent logoutEventPublisher = new OnUserLogoutSuccessEvent(user.getUsername(),token);
        applicationEventPublisher.publishEvent(logoutEventPublisher);
        User currentUser = userRepository.findUserByEmail(user.getUsername());

        if (currentUser.getRole().equals(Role.MENTOR))
            activeMentors--;

        if (currentUser.getRole().equals(Role.MENTEE))
            activeMentees--;

        return "You have successfully logout";
    }
  
    public String checkExpiration(String token) {
        Date expireDateFromToken = jwtProvider.getExpireDateFromToken(token);
       try{
           if(expireDateFromToken.after(Date.from(Instant.now()))){
            return "Token is valid";
        }}catch (ExpiredJwtException e){
           throw new JwtAuthenticationException("Token is invalid");
       }
        throw new JwtAuthenticationException("Token is invalid");
    }

    public int getActiveMentors() {
        return activeMentors;
    }

    public int getActiveMentees() {
        return activeMentees;
    }
}
