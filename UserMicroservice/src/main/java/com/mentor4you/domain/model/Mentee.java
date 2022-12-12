package com.mentor4you.domain.model;

import javax.persistence.*;
import lombok.*;

@Entity
@Table(name = "mentees")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Mentee {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "first_name", nullable = false)
  private String firstName;

  @Column(name = "last_name", nullable = false)
  private String lastName;

  @OneToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;
}
