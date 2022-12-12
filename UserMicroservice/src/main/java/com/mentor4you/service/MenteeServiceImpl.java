package com.mentor4you.service;

import com.mentor4you.config.ActiveMQProperties;
import com.mentor4you.domain.dto.AddMenteeDTO;
import com.mentor4you.domain.dto.UpdateMenteeDTO;
import com.mentor4you.domain.event.UpdateMenteeEvent;
import com.mentor4you.domain.model.Mentee;
import com.mentor4you.domain.model.User;
import com.mentor4you.exception.EntityNotFoundException;
import com.mentor4you.repository.MenteeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MenteeServiceImpl implements MenteeService {

  private final UserService userService;
  private final MenteeRepository menteeRepository;
  private final JmsTemplate jmsTemplate;

  @Override
  public Mentee getMenteeById(Integer menteeId) {
    return menteeRepository.findById(menteeId).orElseThrow(() -> new EntityNotFoundException("Mentee with given id doesn't exists"));
  }

  @Override
  public Mentee addMentee(AddMenteeDTO addMenteeDTO) {
    final User user = userService.getUserById(addMenteeDTO.getUserId());
    return menteeRepository.save(
        Mentee.builder()
            .firstName(addMenteeDTO.getFirstName())
            .lastName(addMenteeDTO.getLastName())
            .user(user)
            .build());
  }

  @Override
  public Mentee updateMentee(UpdateMenteeDTO updateMenteeDTO) {
    final Mentee mentee = menteeRepository.findById(updateMenteeDTO.getId()).orElseThrow(() -> new EntityNotFoundException("Mentee with given id doesn't exists"));
    mentee.setFirstName(updateMenteeDTO.getFirstName());
    mentee.setLastName(updateMenteeDTO.getLastName());
    menteeRepository.save(mentee);
    sendUpdateMenteeEvent(mentee);
    return mentee;
  }

  private void sendUpdateMenteeEvent(Mentee mentee) {
    jmsTemplate.convertAndSend(
        ActiveMQProperties.MENTEE_EVENTS_TOPIC,
        UpdateMenteeEvent.builder()
            .menteeId(mentee.getId())
            .firstName(mentee.getFirstName())
            .lastName(mentee.getLastName())
            .build());
  }
}
