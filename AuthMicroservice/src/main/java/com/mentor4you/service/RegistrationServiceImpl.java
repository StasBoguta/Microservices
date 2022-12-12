package com.mentor4you.service;

import com.mentor4you.domain.DTO.mentee.AddMenteeDTO;
import com.mentor4you.domain.DTO.mentor.AddMentorDTO;
import com.mentor4you.domain.DTO.user.AddUserDTO;
import com.mentor4you.domain.DTO.user.RegisterUserDTO;
import com.mentor4you.domain.model.Role;
import com.mentor4you.domain.model.User;
import com.mentor4you.exception.EntityNotFoundException;
import com.mentor4you.exception.RegistrationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {

  private final UserService userService;
  private final MentorService mentorService;
  private final MenteeService menteeService;
  private final PasswordService passwordService;

  @Override
  public void registerUser(RegisterUserDTO registerUserDTO) {
    if(userExists(registerUserDTO.getEmail())) {
      throw new RegistrationException("User with provided email already exists");
    }

    final AddUserDTO addUserDTO =
        AddUserDTO.builder()
            .email(registerUserDTO.getEmail())
            .password(passwordService.encodePassword(registerUserDTO.getPassword()))
            .role(registerUserDTO.getRole())
            .build();
    final User registeredUser = userService.addUser(addUserDTO);
    if (registeredUser.getRole().equals(Role.MENTEE.name())) {
      final AddMenteeDTO addMenteeDTO =
          AddMenteeDTO.builder()
              .userId(registeredUser.getId())
              .firstName(registerUserDTO.getFirstName())
              .lastName(registerUserDTO.getLastName())
              .build();
      menteeService.addMentee(addMenteeDTO);
    } else if (registeredUser.getRole().equals(Role.MENTOR.name())) {
      final AddMentorDTO addMenteeDTO =
          AddMentorDTO.builder()
              .userId(registeredUser.getId())
              .firstName(registerUserDTO.getFirstName())
              .lastName(registerUserDTO.getLastName())
              .build();
      mentorService.addMentor(addMenteeDTO);
    }
  }

  private boolean userExists(String email) {
    try {
      userService.getUserByEmail(email);
      return true;
    } catch (EntityNotFoundException ex) {
      return false;
    }
  }
}
