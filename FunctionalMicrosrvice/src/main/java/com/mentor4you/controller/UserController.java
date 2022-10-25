package com.mentor4you.controller;

import com.mentor4you.exception.AdminDeleteException;
import com.mentor4you.exception.RegistrationException;
import com.mentor4you.model.DTO.EmailRequest;
import com.mentor4you.model.DTO.PasswordDTO;
import com.mentor4you.model.DTO.UserBanDTO;
import com.mentor4you.model.DTO.UserBanUpdateRequest;
import com.mentor4you.model.User;
import com.mentor4you.repository.UserRepository;
import com.mentor4you.service.EmailService;
import com.mentor4you.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/users")
public class UserController {

    UserService userService;
    UserRepository userRepository;
    EmailService emailService;


    @Autowired
    public UserController(UserService userService, UserRepository userRepository, EmailService emailService) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    //select all accounts
    @Operation(summary = "select all users")
    @GetMapping
    List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @Operation(summary = "change password")
    @PutMapping("/changePassword")
    ResponseEntity<?> changePassword(@RequestBody PasswordDTO dto,
                                     HttpServletRequest request) {

        Map<String, String> res = new HashMap<>();
        try {
            String result = userService.changePassword(request, dto.getOldPassword(), dto.getNewPassword());
            res.put("message", result);
            return ResponseEntity.ok(res);
        } catch (RegistrationException | UsernameNotFoundException e) {
            res.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(res);
        }
    }


    @PostMapping("/updateEmail")
    public String updateEmail(@RequestBody EmailRequest request) {
        return emailService.updateEmail(request.getEmail(), request.getId());
    }

    @Operation(summary = "get banned user")
    @GetMapping("/getAllBannedUser")
    ResponseEntity<List<UserBanDTO>> getAllBannedUser() {
        List<UserBanDTO> list = userService.getAllBannedUsers(true);
        if (list.isEmpty()) {
            return new ResponseEntity(list, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity(list, HttpStatus.OK);
        }
    }


    @Operation(summary = "change User's ban status")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('MODERATOR')")
    @PutMapping("/changeBanToUser")
    ResponseEntity<?> changeBanToUser(@RequestBody UserBanUpdateRequest dto) {
        String result = userService.changeBanToUser(dto.banStatus, dto.getId());
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @Operation(summary = "delete user account")
    @DeleteMapping("/delete")
    ResponseEntity<?> deleteUser(HttpServletRequest request){
        Map<String, String> res = new HashMap<>();

        try {
            String result = userService.deleteUser(request);
            res.put("message", result);
            return ResponseEntity.ok(res);
        } catch (AdminDeleteException | MessagingException e) {
            res.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(res);
        }
    }
    
    @Operation(summary = "change current user`s role")
    @PutMapping("/changeRole")
    ResponseEntity<?> changeRole(@RequestHeader("Authorization") String header) {
        String result = userService.changeMyRole(header);
        return new ResponseEntity<String>(result, HttpStatus.OK);
    }


}
