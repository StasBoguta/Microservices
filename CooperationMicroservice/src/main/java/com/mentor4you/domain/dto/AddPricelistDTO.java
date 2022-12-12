package com.mentor4you.domain.dto;

import java.math.BigDecimal;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddPricelistDTO {

  private Integer categoryId;
  private Integer mentorId;
  private BigDecimal pricePerHour;
  private String currency;
}
