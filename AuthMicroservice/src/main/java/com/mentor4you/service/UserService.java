package com.mentor4you.service;

import com.mentor4you.domain.DTO.user.AddUserDTO;
import com.mentor4you.domain.model.User;

public interface UserService {

  User getUserByEmail(String email);

  User addUser(AddUserDTO addUserDTO);
}
