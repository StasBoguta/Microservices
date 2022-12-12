package com.mentor4you.service;

import com.mentor4you.domain.dto.AddMenteeDTO;
import com.mentor4you.domain.dto.UpdateMenteeDTO;
import com.mentor4you.domain.model.Mentee;

public interface MenteeService {

  Mentee getMenteeById(Integer menteeId);

  Mentee addMentee(AddMenteeDTO addMenteeDTO);

  Mentee updateMentee(UpdateMenteeDTO updateMenteeDTO);
}
