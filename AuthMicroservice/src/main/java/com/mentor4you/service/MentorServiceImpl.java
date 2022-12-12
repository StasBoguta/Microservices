package com.mentor4you.service;

import com.mentor4you.domain.DTO.mentor.AddMentorDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MentorServiceImpl implements MentorService {

  private final UserMicroserviceFeignClient userMicroserviceFeignClient;

  @Override
  public void addMentor(AddMentorDTO addMentorDTO) {
    userMicroserviceFeignClient.addMentor(addMentorDTO);
  }
}
