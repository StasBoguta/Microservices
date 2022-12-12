package com.mentor4you.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mentor4you.config.ActiveMQProperties;
import com.mentor4you.domain.event.UpdateMenteeEvent;
import com.mentor4you.domain.model.Review;
import com.mentor4you.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateMenteeEventHandler {

  private final ObjectMapper objectMapper;
  private final ReviewRepository reviewRepository;

  @JmsListener(
      destination = ActiveMQProperties.MENTEE_EVENTS_TOPIC,
      containerFactory = "topicJmsListenerContainerFactory")
  public void handleUpdateMenteeEvent(String updateMenteeEventString)
      throws JsonProcessingException {
    final UpdateMenteeEvent updateMenteeEvent =
        objectMapper.readValue(updateMenteeEventString, UpdateMenteeEvent.class);
    final Iterable<Review> reviewsOfMentee =
        reviewRepository.findAllReviewsOfMentee(updateMenteeEvent.getMenteeId());
    reviewsOfMentee.forEach(
        review -> {
          review.setMenteeFirstName(updateMenteeEvent.getFirstName());
          review.setMenteeLastName(updateMenteeEvent.getLastName());
        });
    reviewRepository.saveAll(reviewsOfMentee);
  }
}
