package com.example.Microservices.service;

import com.example.Microservices.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final UserRepository userRepository;

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
}
