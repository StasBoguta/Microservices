package com.mentor4you.domain.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddReviewDTO {

  private Integer menteeId;
  private Integer mentorId;
  private String text;
  private Integer rating;
}
