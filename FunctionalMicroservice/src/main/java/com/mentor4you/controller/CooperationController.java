package com.mentor4you.controller;

import com.mentor4you.model.DTO.coopDTO.StatusBoolDTO;
import com.mentor4you.security.jwt.JwtProvider;
import com.mentor4you.service.CooperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/Cooperation")
public class CooperationController {

    @Autowired
    CooperationService cooperationService;

    @Autowired
    JwtProvider jwtProvider;

    @PreAuthorize("hasAuthority('MENTEE')")
    @PostMapping("/{id}")
    public ResponseEntity<String> createCooperation(@PathVariable(value = "id") Integer id, HttpServletRequest request){
       try{

           cooperationService.createCooperation(id,emailFromToken(request));
           return new ResponseEntity<String>(HttpStatus.OK);
       }catch (RuntimeException e){
           return new ResponseEntity<String>(e.getMessage(),HttpStatus.LOCKED);
       }
    }

    @GetMapping("/mentorCooperation")
    public ResponseEntity<?> getCooperationForMentor(HttpServletRequest request){

        return ResponseEntity.ok(cooperationService.getCooperationForMentor(emailFromToken(request)));
    }

    @GetMapping("/menteeCooperation")
    public ResponseEntity<?> getCooperationForMentee(HttpServletRequest request){
        return  ResponseEntity.ok(cooperationService.getCooperationForMentee(emailFromToken(request)));
    }

    @PutMapping("/approve/{id}")
    public ResponseEntity<String> decisionsOnCoop(@PathVariable(value = "id")Integer id, @RequestBody StatusBoolDTO status, HttpServletRequest request){
        try{
            cooperationService.decisionsOnCoop(emailFromToken(request),id,status);
            return new ResponseEntity<String>(HttpStatus.OK);
        }catch (RuntimeException e){
            return new ResponseEntity<String>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/responseMentee/{id}")
    public  ResponseEntity<String> responseMentee(@PathVariable(value = "id")Integer id,HttpServletRequest request){

        try{
            cooperationService.responseMentee(emailFromToken(request),id);
            return new ResponseEntity<String>(HttpStatus.OK);
        }catch (RuntimeException e){
            return new ResponseEntity<String>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/showInformation/{id}")
    public  ResponseEntity<Boolean> showInformation(@PathVariable(value = "id")Integer id,HttpServletRequest request){
        return ResponseEntity.ok(cooperationService.showInformation(emailFromToken(request),id));
    }

    private String emailFromToken(HttpServletRequest request){
        String token =jwtProvider.getTokenFromRequest(request);

        String email =jwtProvider.getLoginFromToken(token);

        return email;
    }
}
