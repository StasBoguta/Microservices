package com.mentor4you.service;

import com.mentor4you.domain.dto.AddReviewDTO;
import com.mentor4you.domain.dto.UpdateReviewDTO;
import com.mentor4you.domain.model.Review;
import java.util.List;

public interface ReviewService {

  List<Review> getAllReviews();

  Review getReviewById(Integer reviewId);

  Review addReview(AddReviewDTO addReviewDTO);

  Review updateReview(UpdateReviewDTO updateReviewDTO);

  void deleteReview(Integer reviewId);
}
