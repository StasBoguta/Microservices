package com.mentor4you.service;

import com.mentor4you.domain.DTO.mentee.AddMenteeDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MenteeServiceImpl implements MenteeService {

  private final UserMicroserviceFeignClient userMicroserviceFeignClient;

  @Override
  public void addMentee(AddMenteeDTO addMenteeDTO) {
    userMicroserviceFeignClient.addMentee(addMenteeDTO);
  }
}
