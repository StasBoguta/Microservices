package com.mentor4you.domain.event;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateMentorEvent {

  private Integer mentorId;
  private String firstName;
  private String lastName;
}
