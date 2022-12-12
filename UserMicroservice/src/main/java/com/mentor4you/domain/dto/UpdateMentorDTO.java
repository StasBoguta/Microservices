package com.mentor4you.domain.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateMentorDTO {

  private Integer id;
  private String firstName;
  private String lastName;
}
