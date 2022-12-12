package com.mentor4you.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PasswordService {

  private final PasswordEncoder passwordEncoder;

  public String encodePassword(String password) {
    return passwordEncoder.encode(password);
  }

  public boolean passwordsEqual(String password, String userPassword) {
    return passwordEncoder.matches(password, userPassword);
  }
}
