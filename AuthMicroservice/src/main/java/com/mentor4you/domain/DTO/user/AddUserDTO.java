package com.mentor4you.domain.DTO.user;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddUserDTO {

  private String email;
  private String password;
  private String role;
}
