package com.mentor4you.domain.event;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateMenteeEvent {

  private Integer menteeId;
  private String firstName;
  private String lastName;
}
