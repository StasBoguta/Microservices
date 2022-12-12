package com.mentor4you.domain.dto;

import com.mentor4you.domain.model.Category;
import java.math.BigDecimal;
import javax.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PricelistDTO {

  private Integer id;
  private Category category;
  private Mentor mentor;
  private BigDecimal pricePerHour;
  private String currency;

  @Getter
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class Category {
    private Integer id;
    private String name;
  }

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
}
