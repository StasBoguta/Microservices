package com.mentor4you.domain.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddMentorDTO {

  private Integer userId;
  private String firstName;
  private String lastName;
}
