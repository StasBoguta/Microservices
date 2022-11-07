package com.mentor4you.service;

public interface AuthService {

    boolean isValidToken(String token);

    String login(String email, String password);
}
