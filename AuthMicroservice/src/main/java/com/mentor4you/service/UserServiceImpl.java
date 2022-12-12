package com.mentor4you.service;

import com.mentor4you.domain.DTO.user.AddUserDTO;
import com.mentor4you.domain.model.User;
import com.mentor4you.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

  private final UserMicroserviceFeignClient userMicroserviceFeignClient;

  @Override
  public User getUserByEmail(String email) {
    try {
      final ResponseEntity<User> response = userMicroserviceFeignClient.getUserByEmail(email);
      return response.getBody();
    } catch (Exception ex) {
      log.error("Feign error: ", ex);
      throw new EntityNotFoundException("User with provided email doesn't exist");
    }
  }

  @Override
  public User addUser(AddUserDTO addUserDTO) {
    return userMicroserviceFeignClient.addUser(addUserDTO).getBody();
  }
}
