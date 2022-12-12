package com.mentor4you.domain.DTO.user;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetUserByEmailDTO {

  private String email;
}
