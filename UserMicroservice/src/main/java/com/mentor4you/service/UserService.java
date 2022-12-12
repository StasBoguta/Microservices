package com.mentor4you.service;

import com.mentor4you.domain.dto.AddUserDTO;
import com.mentor4you.domain.model.User;

public interface UserService {

  User getUserById(Integer userId);

  User getUserByEmail(String userEmail);

  User addUser(AddUserDTO addUserDTO);
}
