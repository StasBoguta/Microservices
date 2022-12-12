package com.mentor4you.domain.model;

import javax.persistence.*;
import lombok.*;

@Entity
@Table(name = "mentors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Mentor {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "first_name", nullable = false)
  private String firstName;

  @Column(name = "last_name", nullable = false)
  private String lastName;

  @Column(nullable = true)
  private Double rating;

  @OneToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;
}
