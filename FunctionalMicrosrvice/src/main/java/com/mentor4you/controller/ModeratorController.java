package com.mentor4you.controller;

import com.mentor4you.model.DTO.ModeratorDTO;
import com.mentor4you.model.DTO.ModeratorResponseDTO;
import com.mentor4you.model.User;
import com.mentor4you.service.ModeratorService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/moderator")
public class ModeratorController {

    @Autowired
    ModeratorService moderatorService;

    public ModeratorController(ModeratorService moderatorService) {
        this.moderatorService = moderatorService;
    }

    @Operation(summary = "select moderator by token")
    @GetMapping("/getModeratorDTO/")
    ResponseEntity<ModeratorDTO> getOneMentorByToken
            (HttpServletRequest req) {
        ModeratorDTO moderatorDTO = moderatorService.getModeratorByToken(req);
        if(moderatorDTO!=null){
            return new ResponseEntity<ModeratorDTO>(moderatorDTO, HttpStatus.OK);
        }else{
            return new ResponseEntity<ModeratorDTO>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "update mentor by token")
    @PutMapping("/updateModerator/")
    ResponseEntity<User> updateModeratorByToken
            (HttpServletRequest req, @RequestBody ModeratorResponseDTO dto) {
        User user =  moderatorService.updateModeratorByToken(dto,req);
        if(user!=null){
            return new ResponseEntity<User>(HttpStatus.OK);
        }else{
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
    }
}
