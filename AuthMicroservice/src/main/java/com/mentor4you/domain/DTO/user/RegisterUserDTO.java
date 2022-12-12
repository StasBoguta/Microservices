package com.mentor4you.domain.DTO.user;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterUserDTO {

  private String email;
  private String password;
  private String role;
  private String firstName;
  private String lastName;
}
