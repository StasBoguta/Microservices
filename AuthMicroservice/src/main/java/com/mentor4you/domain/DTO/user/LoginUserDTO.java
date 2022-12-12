package com.mentor4you.domain.DTO.user;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginUserDTO {

  private String email;
  private String password;
}
