package com.mentor4you.domain.mapper;

import com.mentor4you.domain.dto.AddPricelistDTO;
import com.mentor4you.domain.dto.PricelistDTO;
import com.mentor4you.domain.model.Pricelist;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PricelistMapper {

  @Mapping(target = "id", source = "id")
  @Mapping(target = "pricePerHour", source = "pricePerHour")
  @Mapping(target = "currency", source = "currency")
  @Mapping(target = "category.id", source = "category.id")
  @Mapping(target = "category.name", source = "category.name")
  @Mapping(target = "mentor.id", source = "mentorId")
  @Mapping(target = "mentor.firstName", source = "mentorFirstName")
  @Mapping(target = "mentor.lastName", source = "mentorLastName")
  PricelistDTO toPricelistDTO(Pricelist pricelist);

  @Mapping(target = "pricePerHour", source = "pricePerHour")
  @Mapping(target = "currency", source = "currency")
  @Mapping(target = "mentorId", source = "mentorId")
  Pricelist toPricelist(AddPricelistDTO addPricelistDTO);
}
