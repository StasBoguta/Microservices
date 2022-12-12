package com.mentor4you.service;

import com.mentor4you.domain.dto.AddUserDTO;
import com.mentor4you.domain.model.Role;
import com.mentor4you.domain.model.User;
import com.mentor4you.exception.EntityNotFoundException;
import com.mentor4you.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  @Override
  public User getUserById(Integer userId) {
    return userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User with given id doesn't exists"));
  }

  @Override
  public User getUserByEmail(String userEmail) {
    return userRepository.findUserByEmail(userEmail).orElseThrow(() -> new EntityNotFoundException("User with given email doesn't exists"));
  }

  @Override
  public User addUser(AddUserDTO addUserDTO) {
    return userRepository.save(
        User.builder()
            .email(addUserDTO.getEmail())
            .password(addUserDTO.getPassword())
            .role(Role.fromName(addUserDTO.getRole()))
            .build());
  }
}
