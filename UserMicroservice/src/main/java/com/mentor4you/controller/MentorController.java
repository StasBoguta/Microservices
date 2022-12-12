package com.mentor4you.controller;

import com.mentor4you.domain.dto.AddMentorDTO;
import com.mentor4you.domain.dto.UpdateMentorDTO;
import com.mentor4you.domain.model.Mentor;
import com.mentor4you.service.MentorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mentors")
@RequiredArgsConstructor
public class MentorController {

  private final MentorService mentorService;

  @PostMapping
  public ResponseEntity<Mentor> addMentor(@RequestBody AddMentorDTO addMentorDTO) {
    return ResponseEntity.status(HttpStatus.CREATED).body(mentorService.addMentor(addMentorDTO));
  }

  @PutMapping("/{mentorId}")
  public Mentor updateMentee(
      @PathVariable Integer mentorId, @RequestBody UpdateMentorDTO updateMentorDTO) {
    updateMentorDTO.setId(mentorId);
    return mentorService.updateMentor(updateMentorDTO);
  }
}
