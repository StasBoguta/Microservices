package com.mentor4you.service;

import com.mentor4you.config.ActiveMQProperties;
import com.mentor4you.domain.dto.AddMentorDTO;
import com.mentor4you.domain.dto.UpdateMentorDTO;
import com.mentor4you.domain.event.UpdateMentorEvent;
import com.mentor4you.domain.model.Mentor;
import com.mentor4you.domain.model.User;
import com.mentor4you.exception.EntityNotFoundException;
import com.mentor4you.repository.MentorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MentorServiceImpl implements MentorService {

  private final UserService userService;
  private final MentorRepository mentorRepository;
  private final JmsTemplate jmsTemplate;

  @Override
  public Mentor getMentorById(Integer mentorId) {
    return mentorRepository.findById(mentorId).orElseThrow(() -> new EntityNotFoundException("Mentor with given id doesn't exists"));
  }

  @Override
  public Mentor addMentor(AddMentorDTO addMentorDTO) {
    final User user = userService.getUserById(addMentorDTO.getUserId());
    return mentorRepository.save(
        Mentor.builder()
            .firstName(addMentorDTO.getFirstName())
            .lastName(addMentorDTO.getLastName())
            .rating(null)
            .user(user)
            .build());
  }

  @Override
  public Mentor updateMentor(UpdateMentorDTO updateMentorDTO) {
    final Mentor mentor = mentorRepository.findById(updateMentorDTO.getId()).orElseThrow(() -> new EntityNotFoundException("Mentor with given id doesn't exists"));
    mentor.setFirstName(updateMentorDTO.getFirstName());
    mentor.setLastName(updateMentorDTO.getLastName());
    mentorRepository.save(mentor);
    sendUpdateMentorEvent(mentor);
    return mentor;
  }

  private void sendUpdateMentorEvent(Mentor mentor) {
    jmsTemplate.convertAndSend(
        ActiveMQProperties.MENTOR_EVENTS_TOPIC,
        UpdateMentorEvent.builder()
            .mentorId(mentor.getId())
            .firstName(mentor.getFirstName())
            .lastName(mentor.getLastName())
            .build());
  }
}
