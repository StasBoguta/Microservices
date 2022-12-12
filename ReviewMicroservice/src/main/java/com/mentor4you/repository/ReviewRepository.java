package com.mentor4you.repository;

import com.mentor4you.domain.model.Review;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends CrudRepository<Review, Integer> {

  @Query("SELECT r.rating FROM Review r WHERE r.mentorId = :mentorId")
  Iterable<Integer> findAllRatingsForMentor(@Param("mentorId") Integer mentorId);

  @Query("SELECT r FROM Review r WHERE r.menteeId = :menteeId")
  Iterable<Review> findAllReviewsOfMentee(@Param("menteeId") Integer menteeId);

  @Query("SELECT r FROM Review r WHERE r.mentorId = :mentorId")
  Iterable<Review> findAllReviewsOfMentor(@Param("mentorId") Integer mentorId);
}
