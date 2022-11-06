package com.mentor4you.service;

import com.mentor4you.model.DTO.ModeratorDTO;
import com.mentor4you.model.DTO.ModeratorResponseDTO;
import com.mentor4you.model.Mentors;
import com.mentor4you.model.User;
import com.mentor4you.repository.*;
import com.mentor4you.security.jwt.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class ModeratorService {
    @Autowired
    CityToMentorRepository cityToMentorRepository;
    MentorsToCategory mentorsToCategory;
    AccountRepository accountRepository;
    LanguagesService languagesService;
    MentorRepository mentorRepository;
    UserRepository userRepository;
    MenteeService menteeService;
    UserService userService;
    JwtProvider jwtProvider;
    EmailService emailService;

    public ModeratorService(CityToMentorRepository cityToMentorRepository,
                            MentorsToCategory mentorsToCategory,
                            AccountRepository accountRepository,
                            LanguagesService languagesService,
                            MentorRepository mentorRepository,
                            UserRepository userRepository,
                            MenteeService menteeService,
                            UserService userService,
                            JwtProvider jwtProvider,
                            EmailService emailService) {
        this.cityToMentorRepository = cityToMentorRepository;
        this.mentorsToCategory = mentorsToCategory;
        this.accountRepository = accountRepository;
        this.languagesService = languagesService;
        this.mentorRepository = mentorRepository;
        this.userRepository = userRepository;
        this.menteeService = menteeService;
        this.userService = userService;
        this.jwtProvider = jwtProvider;
        this.emailService = emailService;
    }

    public ModeratorDTO getModeratorByToken(HttpServletRequest request){
        String token = jwtProvider.getTokenFromRequest(request);
        String email = jwtProvider.getLoginFromToken(token);
        User user = userRepository.findUserByEmail(email);

        if(user!=null){
            ModeratorDTO dto =new ModeratorDTO();
            dto.setId(user.getId());
            dto.setAvatar(user.getAvatar());
            dto.setEmail(user.getEmail());
            dto.setFirstName(user.getFirst_name());
            dto.setLastName(user.getLast_name());

            return dto;
        }
        else{
            return null;}
    }

    public User updateModeratorByToken(ModeratorResponseDTO dto,
                                                         HttpServletRequest request) {

        String token = jwtProvider.getTokenFromRequest(request);
        String email = jwtProvider.getLoginFromToken(token);
        User userToUpdate = userRepository.findUserByEmail(email);

        if(userToUpdate!=null) {
            if(dto.getFirstName().isEmpty()){userToUpdate.setFirst_name("");}
            else{userToUpdate.setFirst_name(dto.getFirstName());}

            if(dto.getLastName().isEmpty()){userToUpdate.setLast_name("");}
            else{ userToUpdate.setLast_name(dto.getLastName());}

            //update email using method from emailService
            //if emails are equals do nothing
            if (!userToUpdate.getEmail().equals(dto.getEmail())) {
                //TODO create new token with new email
                String reportUpdate = emailService.updateEmail(userToUpdate.getEmail(), userToUpdate.getId());
            }
            userRepository.save(userToUpdate);
            return userToUpdate;
        }
        else return  userToUpdate;

    }
    public void remove(Mentors m){
        if(m.getMentors_to_categories()!=null)
            mentorsToCategory.removeByMentors(m);
    }
}
