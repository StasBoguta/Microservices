package com.mentor4you.controller;

import com.mentor4you.model.DTO.UpdateUserEmailDTO;
import com.mentor4you.model.User;
import com.mentor4you.repository.UserRepository;
import com.mentor4you.service.EmailService;
import com.mentor4you.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;

import java.util.List;


@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PutMapping("/{userId}")
    public ResponseEntity<?> updateUserEmail(@PathVariable(name = "userId") Integer userId,
                                                @RequestBody UpdateUserEmailDTO updateUserEmailDTO) {
        userService.updateUserEmail(userId, updateUserEmailDTO.getEmail());
        return ResponseEntity.ok().build();
    }

}
