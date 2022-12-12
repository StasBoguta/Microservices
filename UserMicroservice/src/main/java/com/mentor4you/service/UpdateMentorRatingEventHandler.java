package com.mentor4you.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mentor4you.config.ActiveMQProperties;
import com.mentor4you.domain.event.UpdateMentorRatingEvent;
import com.mentor4you.domain.model.Mentor;
import com.mentor4you.repository.MentorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateMentorRatingEventHandler {

  private final ObjectMapper objectMapper;
  private final MentorRepository mentorRepository;

  @JmsListener(
      destination = ActiveMQProperties.MENTOR_EVENTS_QUEUE,
      containerFactory = "queueJmsListenerContainerFactory")
  public void handleUpdateMentorRatingEvent(String updateMentorRatingEventString)
      throws JsonProcessingException {
    UpdateMentorRatingEvent updateMentorRatingEvent =
        objectMapper.readValue(updateMentorRatingEventString, UpdateMentorRatingEvent.class);
    final Mentor mentor = mentorRepository.findById(updateMentorRatingEvent.getMentorId()).get();
    mentor.setRating(updateMentorRatingEvent.getNewRating());
    mentorRepository.save(mentor);
  }
}
