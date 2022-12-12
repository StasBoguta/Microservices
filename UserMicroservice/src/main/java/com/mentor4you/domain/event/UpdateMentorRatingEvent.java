package com.mentor4you.domain.event;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateMentorRatingEvent {

  private Integer mentorId;
  private Double newRating;
}
