package com.mentor4you.controller;

import com.mentor4you.domain.DTO.user.RegisterUserDTO;
import com.mentor4you.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/registration")
@RequiredArgsConstructor
public class RegistrationController {

  private final RegistrationService registrationService;

  @PostMapping
  ResponseEntity<?> registration(@RequestBody RegisterUserDTO registerUserDTO) {
    registrationService.registerUser(registerUserDTO);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }
}
