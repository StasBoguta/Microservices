package com.mentor4you.domain.model;

import javax.persistence.*;
import lombok.*;

@Entity
@Table(name = "reviews")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "mentee_id", nullable = false)
  private Integer menteeId;

  @Column(name = "mentee_first_name")
  private String menteeFirstName;

  @Column(name = "mentee_last_name")
  private String menteeLastName;

  @Column(name = "mentor_id", nullable = false)
  private Integer mentorId;

  @Column(name = "mentor_first_name")
  private String mentorFirstName;

  @Column(name = "mentor_last_name")
  private String mentorLastName;

  @Column(nullable = true)
  private String text;

  @Column(nullable = false)
  private Integer rating;

  @Column(name = "created_at", nullable = false)
  private Long createdAt;
}
