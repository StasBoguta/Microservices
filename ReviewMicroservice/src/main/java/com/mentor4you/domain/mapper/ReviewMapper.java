package com.mentor4you.domain.mapper;

import com.mentor4you.domain.dto.AddReviewDTO;
import com.mentor4you.domain.dto.ReviewDTO;
import com.mentor4you.domain.model.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", imports = java.util.Date.class)
public interface ReviewMapper {

  @Mapping(target = "id", source = "id")
  @Mapping(target = "text", source = "text")
  @Mapping(target = "rating", source = "rating")
  @Mapping(target = "createdAt", source = "createdAt")
  @Mapping(target = "mentee.id", source = "menteeId")
  @Mapping(target = "mentee.firstName", source = "menteeFirstName")
  @Mapping(target = "mentee.lastName", source = "menteeLastName")
  @Mapping(target = "mentor.id", source = "mentorId")
  @Mapping(target = "mentor.firstName", source = "mentorFirstName")
  @Mapping(target = "mentor.lastName", source = "mentorLastName")
  ReviewDTO toReviewDTO(Review review);

  @Mapping(target = "text", source = "text")
  @Mapping(target = "rating", source = "rating")
  @Mapping(target = "createdAt", expression = "java(new Date().getTime())")
  @Mapping(target = "menteeId", source = "menteeId")
  @Mapping(target = "mentorId", source = "mentorId")
  Review toReview(AddReviewDTO addReviewDTO);
}
