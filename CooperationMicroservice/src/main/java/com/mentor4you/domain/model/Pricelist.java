package com.mentor4you.domain.model;

import java.math.BigDecimal;
import javax.persistence.*;
import lombok.*;

@Entity
@Table(name = "pricelists")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pricelist {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @ManyToOne
  @JoinColumn(name = "category_id", nullable = false)
  private Category category;

  @Column(nullable = false)
  private Integer mentorId;

  @Column(name = "mentor_first_name", nullable = false)
  private String mentorFirstName;

  @Column(name = "mentor_last_name", nullable = false)
  private String mentorLastName;

  @Column(name = "price_per_hour", nullable = false)
  private BigDecimal pricePerHour;

  @Column(nullable = false)
  private String currency;
}
