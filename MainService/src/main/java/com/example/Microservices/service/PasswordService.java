package com.example.Microservices.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PasswordService {

    private final PasswordEncoder passwordEncoder;

    //check is valid password
     public boolean isValidPassword(String password) {
         String reqExp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$";
         return password.matches(reqExp);
     }

     public String encodePassword(String password){
         return passwordEncoder.encode(password);
     }

     public boolean equalsPassword(String password,String userPassword) {
        return passwordEncoder.matches(password,userPassword);
    }
}
