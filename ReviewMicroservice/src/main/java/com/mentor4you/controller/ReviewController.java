package com.mentor4you.controller;

import com.mentor4you.domain.mapper.ReviewMapper;
import com.mentor4you.domain.dto.AddReviewDTO;
import com.mentor4you.domain.dto.ReviewDTO;
import com.mentor4you.domain.dto.UpdateReviewDTO;
import com.mentor4you.service.ReviewService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

  private final ReviewService reviewService;
  private final ReviewMapper reviewMapper;

  @GetMapping
  public List<ReviewDTO> getAllReviews() {
    return reviewService.getAllReviews().stream().map(reviewMapper::toReviewDTO).toList();
  }

  @GetMapping("/{reviewId}")
  public ReviewDTO getReviewById(@PathVariable Integer reviewId) {
    return reviewMapper.toReviewDTO(reviewService.getReviewById(reviewId));
  }

  @PostMapping
  public ResponseEntity<?> addReview(@RequestBody AddReviewDTO addReviewDTO) {
    reviewService.addReview(addReviewDTO);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @PutMapping("/{reviewId}")
  public ReviewDTO updateReview(
      @PathVariable Integer reviewId, @RequestBody UpdateReviewDTO updateReviewDTO) {
    updateReviewDTO.setId(reviewId);
    return reviewMapper.toReviewDTO(reviewService.updateReview(updateReviewDTO));
  }

  @DeleteMapping("/{reviewId}")
  public ResponseEntity<?> deleteReview(@PathVariable Integer reviewId) {
    reviewService.deleteReview(reviewId);
    return ResponseEntity.ok().build();
  }
}
