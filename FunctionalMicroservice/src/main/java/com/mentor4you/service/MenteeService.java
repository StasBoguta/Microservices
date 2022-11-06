package com.mentor4you.service;

import com.mentor4you.exception.MenteeNotFoundException;
import com.mentor4you.exception.MentorNotFoundException;
import com.mentor4you.model.DTO.MenteeResponseDTO;
import com.mentor4you.model.Mentees;
import com.mentor4you.model.Role;
import com.mentor4you.model.User;
import com.mentor4you.repository.AccountRepository;
import com.mentor4you.repository.ContactsToAccountsRepository;
import com.mentor4you.repository.MenteeRepository;
import com.mentor4you.repository.UserRepository;
import com.mentor4you.security.jwt.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MenteeService
{
    @Autowired
    AccountRepository accountRepository;
    MenteeRepository menteeRepository;
    JwtProvider jwtProvider;
    UserRepository userRepository;
    ContactsToAccountsRepository contactsToAccountsRepository;
    EmailService emailService;
    TypeContactsService typeContactsService;
    ContactsToAccountsService contactsToAccountsService;
    UserService userService;

    public MenteeService(AccountRepository accountRepository,
                         MenteeRepository menteeRepository,
                         JwtProvider jwtProvider,
                         UserRepository userRepository,
                         ContactsToAccountsRepository contactsToAccountsRepository,
                         EmailService emailService,
                         TypeContactsService typeContactsService,
                         ContactsToAccountsService contactsToAccountsService,
                         UserService userService) {
        this.accountRepository = accountRepository;
        this.menteeRepository = menteeRepository;
        this.jwtProvider = jwtProvider;
        this.userRepository = userRepository;
        this.contactsToAccountsRepository = contactsToAccountsRepository;
        this.emailService = emailService;
        this.typeContactsService = typeContactsService;
        this.contactsToAccountsService = contactsToAccountsService;
        this.userService=userService;
    }

    public List<Mentees> getFullInfoAllMentees(){
        int theMentees = accountRepository.findByRole(Role.MENTEE).size();
        if(theMentees!=0){
            return menteeRepository.findAll().stream().filter(mentees -> mentees.getAccounts().getUser().getStatus()).collect(Collectors.toList());
        }
        throw new MentorNotFoundException("Mentees not found");

    }

    public Optional<Mentees> getMenteeById(int id){
        Optional<Mentees> theMentee = menteeRepository.findById(id).stream().filter(e->e.getId()==id).findFirst();
        if(theMentee.isPresent()) {
            return theMentee;
        }
        throw new MenteeNotFoundException("Mentee with id = "+ id +" not found");
    }

    public ResponseEntity<MenteeResponseDTO> getOneMenteeByToken(HttpServletRequest req)
    {
        String token = jwtProvider.getTokenFromRequest(req);

        String emailMy = jwtProvider.getLoginFromToken(token);

        User user = userRepository.findUserByEmail(emailMy);

        if(user.getRole()==Role.MENTEE)
            return  userService.getOneMentee(user);
        else return new ResponseEntity<MenteeResponseDTO>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<String> updateUserByToken(MenteeResponseDTO request,
                                                    HttpServletRequest req){
        String token = jwtProvider.getTokenFromRequest(req);

        String emailFromToken = jwtProvider.getLoginFromToken(token);

        User userToUpdate = userRepository.findUserByEmail(emailFromToken);

        if(userToUpdate.getRole() ==Role.MENTEE)
            return userService.updateUser(userToUpdate,request);
        else return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
    }
}
