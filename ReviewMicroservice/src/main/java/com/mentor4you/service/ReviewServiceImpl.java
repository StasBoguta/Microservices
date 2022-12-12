package com.mentor4you.service;

import com.google.common.util.concurrent.AtomicDouble;
import com.mentor4you.config.ActiveMQProperties;
import com.mentor4you.domain.mapper.ReviewMapper;
import com.mentor4you.domain.dto.AddReviewDTO;
import com.mentor4you.domain.dto.UpdateReviewDTO;
import com.mentor4you.domain.event.UpdateMentorRatingEvent;
import com.mentor4you.domain.model.Review;
import com.mentor4you.exception.EntityNotFoundException;
import com.mentor4you.repository.ReviewRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

  private final ReviewRepository reviewRepository;
  private final ReviewMapper reviewMapper;

  private final JmsTemplate mentorJmsTemplate;

  @Override
  public List<Review> getAllReviews() {
    final List<Review> reviews = new ArrayList<>();
    reviewRepository.findAll().forEach(reviews::add);
    return reviews;
  }

  @Override
  public Review getReviewById(Integer reviewId) {
    return reviewRepository.findById(reviewId).orElseThrow(() -> new EntityNotFoundException("Review with provided id doesn't exist"));
  }

  @Override
  public Review addReview(AddReviewDTO addReviewDTO) {
    final Review addedReview = reviewRepository.save(reviewMapper.toReview(addReviewDTO));
    final Integer mentorId = addedReview.getMentorId();
    sendUpdateMentorRatingEvent(mentorId);
    return addedReview;
  }

  @Override
  public Review updateReview(UpdateReviewDTO updateReviewDTO) {
    final Review reviewToUpdate = reviewRepository.findById(updateReviewDTO.getId()).orElseThrow(() -> new EntityNotFoundException("Review with provided id doesn't exist"));
    reviewToUpdate.setText(updateReviewDTO.getText());
    reviewToUpdate.setRating(updateReviewDTO.getRating());
    reviewRepository.save(reviewToUpdate);
    sendUpdateMentorRatingEvent(reviewToUpdate.getMentorId());
    return reviewToUpdate;
  }

  @Override
  public void deleteReview(Integer reviewId) {
    final Review reviewToDelete = reviewRepository.findById(reviewId).orElseThrow(() -> new EntityNotFoundException("Review with provided id doesn't exist"));
    final Integer mentorId = reviewToDelete.getMentorId();
    reviewRepository.deleteById(reviewId);
    sendUpdateMentorRatingEvent(mentorId);
  }

  private void sendUpdateMentorRatingEvent(Integer mentorId) {
    final AtomicDouble newMentorRating = new AtomicDouble(0.0D);
    final AtomicInteger totalReviews = new AtomicInteger(0);
    reviewRepository
        .findAllRatingsForMentor(mentorId)
        .forEach(
            score -> {
              totalReviews.incrementAndGet();
              newMentorRating.addAndGet(score);
            });
    final UpdateMentorRatingEvent updateMentorRatingEvent =
        UpdateMentorRatingEvent.builder()
            .mentorId(mentorId)
            .newRating(totalReviews.get() > 0 ? newMentorRating.get() / totalReviews.get() : null)
            .build();
    mentorJmsTemplate.convertAndSend(
        ActiveMQProperties.MENTOR_EVENTS_QUEUE, updateMentorRatingEvent);
  }
}
