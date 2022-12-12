package com.mentor4you.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mentor4you.config.ActiveMQProperties;
import com.mentor4you.domain.event.UpdateMentorEvent;
import com.mentor4you.domain.model.Review;
import com.mentor4you.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateMentorEventHandler {

  private final ObjectMapper objectMapper;
  private final ReviewRepository reviewRepository;

  @JmsListener(
      destination = ActiveMQProperties.MENTOR_EVENTS_TOPIC,
      containerFactory = "topicJmsListenerContainerFactory")
  public void handleUpdateMentorEvent(String updateMentorEventString)
      throws JsonProcessingException {
    final UpdateMentorEvent updateMentorEvent =
        objectMapper.readValue(updateMentorEventString, UpdateMentorEvent.class);
    final Iterable<Review> reviewsOfMentor =
        reviewRepository.findAllReviewsOfMentor(updateMentorEvent.getMentorId());
    reviewsOfMentor.forEach(
        review -> {
          review.setMentorFirstName(updateMentorEvent.getFirstName());
          review.setMentorLastName(updateMentorEvent.getLastName());
        });
    reviewRepository.saveAll(reviewsOfMentor);
  }
}
