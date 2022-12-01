package com.mentor4you.controller;

import com.mentor4you.model.DTO.LoginDTO;
import com.mentor4you.model.proto.JwtToken;
import com.mentor4you.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthProtobufController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login/proto")
    public ResponseEntity<?> login(@RequestBody LoginDTO request){
        try {
            String token = authenticationService.login(request);
            JwtToken.JwtTokenProto tokenResponse = JwtToken.JwtTokenProto.newBuilder().setToken(token).build();
            return ResponseEntity.ok(tokenResponse);
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
}
