package com.mentor4you.service;

import com.mentor4you.domain.DTO.jwt.JwtTokenResponse;
import com.mentor4you.domain.DTO.user.LoginUserDTO;
import com.mentor4you.domain.DTO.user.LogoutUserDTO;

public interface AuthenticationService {

  JwtTokenResponse loginUser(LoginUserDTO loginUserDTO);

  void logoutUser(LogoutUserDTO logoutUserDTO);
}
