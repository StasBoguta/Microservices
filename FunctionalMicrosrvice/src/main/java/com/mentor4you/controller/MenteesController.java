package com.mentor4you.controller;

import com.mentor4you.exception.ErrorObject;
import com.mentor4you.exception.MenteeNotFoundException;
import com.mentor4you.model.DTO.MenteeResponseDTO;
import com.mentor4you.model.Mentees;
import com.mentor4you.repository.ContactsToAccountsRepository;
import com.mentor4you.repository.TypeContactsRepository;
import com.mentor4you.repository.UserRepository;
import com.mentor4you.security.jwt.JwtProvider;
import com.mentor4you.service.*;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/mentees")
public class MenteesController {


    @Autowired
    MenteeService menteesService;
    UserRepository userRepository;
    ContactsToAccountsRepository contactsToAccountsRepository;
    EmailService emailService;
    TypeContactsRepository typeContactsRepository;
    ContactsToAccountsService contactsToAccountsService;
    TypeContactsService typeContactsService;
    JwtProvider jwtProvider;
    UserService userService;

    public MenteesController(MenteeService menteesService,
                             UserRepository userRepository,
                             ContactsToAccountsRepository contactsToAccountsRepository,
                             EmailService emailService,
                             TypeContactsRepository typeContactsRepository,
                             ContactsToAccountsService contactsToAccountsService,
                             TypeContactsService typeContactsService,
                             JwtProvider jwtProvider,
                             UserService userService) {
        this.menteesService = menteesService;
        this.userRepository = userRepository;
        this.contactsToAccountsRepository = contactsToAccountsRepository;
        this.emailService = emailService;
        this.typeContactsRepository = typeContactsRepository;
        this.contactsToAccountsService = contactsToAccountsService;
        this.typeContactsService = typeContactsService;
        this.jwtProvider = jwtProvider;
        this.userService = userService;
    }

    //select mentees by id
    @GetMapping("/{id}")
    Optional<Mentees> getMenteeById(@PathVariable(value = "id") Integer id) {
        return menteesService.getMenteeById(id);
    }

    @Operation(summary = "info about mentees")
    @GetMapping
    List<Mentees> getAllMentee() {
        return menteesService.getFullInfoAllMentees();
    }

    @ExceptionHandler
    public ResponseEntity<ErrorObject> handleException(MenteeNotFoundException ex) {
        ErrorObject eObject = new ErrorObject();
        eObject.setStatus(HttpStatus.NOT_FOUND.value());
        eObject.setMessage(ex.getMessage());
        eObject.setTimestamp(System.currentTimeMillis());
        return new ResponseEntity<>(eObject, HttpStatus.NOT_FOUND);
    }

    //select mentee by email
    @Operation(summary = "select mentee by token")
    @GetMapping("/getMenteeDTO/")
    ResponseEntity<MenteeResponseDTO> getOneMenteeByToken
    (HttpServletRequest req) {
        return menteesService.getOneMenteeByToken(req);
    }

    @Operation(summary = "update mentee by email")
    @PutMapping("/updateMenteeByEmail")
    public ResponseEntity<String> updateMenteeByToken(@RequestBody MenteeResponseDTO request,
                                                      HttpServletRequest req) {

      return menteesService.updateUserByToken(request, req);
    }
}






