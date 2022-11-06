package com.mentor4you.controller;

import com.mentor4you.exception.ErrorObject;
import com.mentor4you.exception.MentorNotFoundException;
import com.mentor4you.model.DTO.mentorsExtendedInfo.MentorGeneralResponseDTO;
import com.mentor4you.model.DTO.mentorsExtendedInfo.MentorGeneralResponseIdDTO;
import com.mentor4you.model.Mentors;
import com.mentor4you.service.MentorService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/mentors")
public class MentorController {

    @Autowired
    MentorService mentorService;

    public MentorController(MentorService mentorService) {
        this.mentorService = mentorService;
    }


    //select mentor by id
    @Operation(summary = "select mentor by id")
    @GetMapping("/{id}")
    ResponseEntity<MentorGeneralResponseIdDTO> getMentorById(@PathVariable(value = "id") Integer id){
        return mentorService.getMentorById(id);
    }

    @Operation(summary = "select mentor by token")
    @GetMapping("/getMentorDTO/")
    ResponseEntity<MentorGeneralResponseDTO> getOneMentorByToken
            (HttpServletRequest req) {
        return mentorService.getOneMentorByToken(req);
    }
    @Operation(summary = "update mentor by token")
    @PutMapping("/UpdateMentor/")
    ResponseEntity<String> updateMentorByToken
            (HttpServletRequest req,@RequestBody MentorGeneralResponseDTO dto) {
        return mentorService.updateMentorByToken(dto,req);
    }

    @Operation(summary = "Full info about mentors", description = "This method provides the most complete information about existing mentors")
    @GetMapping
    List<Mentors> getAllMentor(){
        return mentorService.getFullInfoAllMentors();
    }

    @ExceptionHandler
    public ResponseEntity<ErrorObject> handleException(MentorNotFoundException ex) {
        ErrorObject eObject = new ErrorObject();
        eObject.setStatus(HttpStatus.NOT_FOUND.value());
        eObject.setMessage(ex.getMessage());
        eObject.setTimestamp(System.currentTimeMillis());
        return new ResponseEntity<>(eObject, HttpStatus.NOT_FOUND);
    }

}
