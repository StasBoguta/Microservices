package com.mentor4you.controller;

import com.mentor4you.domain.DTO.jwt.JwtTokenResponse;
import com.mentor4you.domain.DTO.user.LoginUserDTO;
import com.mentor4you.domain.DTO.user.LogoutUserDTO;
import com.mentor4you.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

  private final AuthenticationService authenticationService;

  @PostMapping("/login")
  public JwtTokenResponse loginUser(@RequestBody LoginUserDTO loginUserDTO) {
    return authenticationService.loginUser(loginUserDTO);
  }

  @PostMapping("/logout")
  public ResponseEntity<?> logoutUser(@RequestBody LogoutUserDTO logoutUserDTO) {
    authenticationService.logoutUser(logoutUserDTO);
    return ResponseEntity.ok().build();
  }
}
