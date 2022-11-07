package com.mentor4you.security;

import com.mentor4you.config.Credentials;
import com.mentor4you.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class JwtProvider {

    private final AuthService authService;
    private final Credentials credentials;

    private String token;

    public synchronized String getToken() {
        if(Objects.isNull(token)) {
            token = authService.login(credentials.getEmail(), credentials.getPassword());
        }

        return token;
    }
}
