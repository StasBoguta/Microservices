package com.mentor4you.domain.DTO.mentee;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddMenteeDTO {

  private Integer userId;
  private String firstName;
  private String lastName;
}
