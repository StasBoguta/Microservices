package com.mentor4you.service;

import com.mentor4you.domain.DTO.mentee.AddMenteeDTO;
import com.mentor4you.domain.DTO.mentor.AddMentorDTO;
import com.mentor4you.domain.DTO.user.AddUserDTO;
import com.mentor4you.domain.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "user-microservice", url = "${web.user-service.url}")
public interface UserMicroserviceFeignClient {

  @GetMapping("${web.user-service.users-endpoint}")
  ResponseEntity<User> getUserByEmail(@RequestParam(name = "email") String email);

  @PostMapping("${web.user-service.users-endpoint}")
  ResponseEntity<User> addUser(@RequestBody AddUserDTO addUserDTO);

  @PostMapping("${web.user-service.mentors-endpoint}")
  ResponseEntity<?> addMentor(@RequestBody AddMentorDTO addMentorDTO);

  @PostMapping("${web.user-service.mentees-endpoint}")
  ResponseEntity<?> addMentee(@RequestBody AddMenteeDTO addMenteeDTO);
}
