package com.mentor4you.domain.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewDTO {

  private Integer id;
  private Mentor mentor;
  private Mentee mentee;
  private String text;
  private Integer rating;
  private Long createdAt;

  @Getter
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class Mentor {
    private Integer id;
    private String firstName;
    private String lastName;
  }

  @Getter
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class Mentee {
    private Integer id;
    private String firstName;
    private String lastName;
  }
}
