package com.mentor4you.service;

import com.mentor4you.domain.DTO.jwt.JwtTokenResponse;
import com.mentor4you.domain.DTO.user.LoginUserDTO;
import com.mentor4you.domain.DTO.user.LogoutUserDTO;
import com.mentor4you.domain.model.User;
import com.mentor4you.exception.LoginException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

  private final UserService userService;
  private final PasswordService passwordService;
  private final JwtTokenService jwtTokenService;

  @Override
  public JwtTokenResponse loginUser(LoginUserDTO loginUserDTO) {
    final User user = userService.getUserByEmail(loginUserDTO.getEmail());

    if (!passwordService.passwordsEqual(loginUserDTO.getPassword(), user.getPassword())) {
      throw new LoginException("Wrong password");
    }

    return new JwtTokenResponse(
        jwtTokenService.createToken(user.getId(), user.getEmail(), user.getRole()));
  }

  @Override
  public void logoutUser(LogoutUserDTO logoutUserDTO) {
    jwtTokenService.deleteToken(logoutUserDTO.getId());
  }
}
