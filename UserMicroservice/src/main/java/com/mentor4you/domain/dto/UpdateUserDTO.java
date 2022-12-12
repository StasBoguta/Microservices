package com.mentor4you.domain.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateUserDTO {

  private Integer id;
  private String email;
  private String password;
}
