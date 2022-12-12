package com.mentor4you.domain.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

  private Integer id;
  private String email;
  private String password;
  private String role;
}
