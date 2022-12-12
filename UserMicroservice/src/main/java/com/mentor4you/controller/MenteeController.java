package com.mentor4you.controller;

import com.mentor4you.domain.dto.AddMenteeDTO;
import com.mentor4you.domain.dto.UpdateMenteeDTO;
import com.mentor4you.domain.model.Mentee;
import com.mentor4you.service.MenteeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mentees")
@RequiredArgsConstructor
public class MenteeController {

  private final MenteeService menteeService;

  @PostMapping
  public ResponseEntity<Mentee> addMentee(@RequestBody AddMenteeDTO addMenteeDTO) {
    return ResponseEntity.status(HttpStatus.CREATED).body(menteeService.addMentee(addMenteeDTO));
  }

  @PutMapping("/{menteeId}")
  public Mentee updateMentee(
      @PathVariable Integer menteeId, @RequestBody UpdateMenteeDTO updateMenteeDTO) {
    updateMenteeDTO.setId(menteeId);
    return menteeService.updateMentee(updateMenteeDTO);
  }
}
