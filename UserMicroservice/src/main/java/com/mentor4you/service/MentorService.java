package com.mentor4you.service;

import com.mentor4you.domain.dto.AddMentorDTO;
import com.mentor4you.domain.dto.UpdateMentorDTO;
import com.mentor4you.domain.model.Mentor;

public interface MentorService {

  Mentor getMentorById(Integer mentorId);

  Mentor addMentor(AddMentorDTO addMentorDTO);

  Mentor updateMentor(UpdateMentorDTO updateMentorDTO);
}
