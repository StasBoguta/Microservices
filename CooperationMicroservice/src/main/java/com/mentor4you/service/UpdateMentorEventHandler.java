package com.mentor4you.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mentor4you.config.ActiveMQProperties;
import com.mentor4you.domain.event.UpdateMentorEvent;
import com.mentor4you.domain.model.Pricelist;
import com.mentor4you.repository.PricelistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateMentorEventHandler {

  private final ObjectMapper objectMapper;
  private final PricelistRepository pricelistRepository;

  @JmsListener(
      destination = ActiveMQProperties.MENTOR_EVENTS_TOPIC,
      containerFactory = "topicJmsListenerContainerFactory")
  public void handleUpdateMentorEvent(String updateMentorEventString)
      throws JsonProcessingException {
    final UpdateMentorEvent updateMentorEvent =
        objectMapper.readValue(updateMentorEventString, UpdateMentorEvent.class);
    final Iterable<Pricelist> pricelistsOfMentor =
        pricelistRepository.findAllPricelistsOfMentor(updateMentorEvent.getMentorId());
    pricelistsOfMentor.forEach(
        pricelist -> {
          pricelist.setMentorFirstName(updateMentorEvent.getFirstName());
          pricelist.setMentorLastName(updateMentorEvent.getLastName());
        });
    pricelistRepository.saveAll(pricelistsOfMentor);
  }
}
