package com.mentor4you.controller;


import com.mentor4you.config.ActiveUsersMetrics;
import com.mentor4you.exception.JwtAuthenticationException;
import com.mentor4you.model.DTO.LoginDTO;
import com.mentor4you.security.jwt.CustomUserDetails;
import com.mentor4you.security.jwt.cache.CurrentUser;
import com.mentor4you.service.AuthenticationService;
import com.mentor4you.service.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationService authenticationService;
    UserService userService;

    public AuthController(AuthenticationService authenticationService, UserService userService) {
        this.authenticationService = authenticationService;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO request){
        Map<String, String> res = new HashMap<>();
        try {
            String token = authenticationService.login(request);
            res.put("message","You have successfully logged in");
            res.put("token", token);
            return ResponseEntity.ok(res);
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseEntity.badRequest().body(res);
        }
    }

    @PutMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, @CurrentUser CustomUserDetails user){
        Map<String, String> res = new HashMap<>();
        String message = authenticationService.logout(request, user);
        res.put("message", message);
        return ResponseEntity.ok(res);
    }

    @PostMapping("/check")
    public ResponseEntity<?> checkTokenExpire(@RequestBody Map<String,String> request){
        Map<String, String> res = new HashMap<>();
        try{
            String message = authenticationService.checkExpiration(request.get("token"));
            res.put("message", message);
            return ResponseEntity.ok(res);
        }catch (JwtAuthenticationException | ExpiredJwtException e){
            res.put("message",e.getMessage());
            return ResponseEntity.status(401).body(res);
        }
    }
}
