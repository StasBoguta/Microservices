package com.mentor4you.controller;

import com.mentor4you.domain.dto.AddUserDTO;
import com.mentor4you.domain.model.User;
import com.mentor4you.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @GetMapping("/{userId}")
  public User getUserById(@PathVariable Integer userId) {
    return userService.getUserById(userId);
  }

  @GetMapping
  public User getUserByEmail(@RequestParam String email) {
    return userService.getUserByEmail(email);
  }

  @PostMapping
  public ResponseEntity<User> addUser(@RequestBody AddUserDTO addUserDTO) {
    return ResponseEntity.status(HttpStatus.CREATED).body(userService.addUser(addUserDTO));
  }
}
