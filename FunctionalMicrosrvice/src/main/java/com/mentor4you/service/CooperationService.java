package com.mentor4you.service;


import com.mentor4you.model.Cooperation;
import com.mentor4you.model.DTO.MinUserDTO;
import com.mentor4you.model.DTO.coopDTO.CoopStatus;
import com.mentor4you.model.DTO.coopDTO.DTOstatusCoopMentee;
import com.mentor4you.model.DTO.coopDTO.StatusBoolDTO;
import com.mentor4you.model.Mentees;
import com.mentor4you.model.Mentors;
import com.mentor4you.model.User;
import com.mentor4you.repository.CooperationRepository;
import com.mentor4you.repository.MenteeRepository;
import com.mentor4you.repository.MentorRepository;
import com.mentor4you.repository.UserRepository;
import com.mentor4you.security.jwt.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Transactional
@Service
public class CooperationService {
    @Autowired
    CooperationRepository cooperationRepository;

    @Autowired
    MentorRepository mentorRepository;

    @Autowired
    MenteeRepository menteeRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtProvider jwtProvider;

    public String createCooperation(int id, String email){


        Mentors  mentor =mentorRepository.getById(id);

        Mentees mentee =menteeRepository.getById(userRepository.findUserByEmail(email).getId());
        Cooperation c =null;

            try {
                c =cooperationRepository.coopIsPresent(mentee.getId(),mentor.getId());
                if(c.getStatus() != CoopStatus.REJECTED)
                    throw  new RuntimeException("Cooperation already exists");
            }
            catch(NullPointerException exception){
                c =new Cooperation();
                c.setMentors(mentor);
                c.setMentees(mentee);
            }
            finally {
                    c.setStatus(CoopStatus.CREATED);
                    cooperationRepository.save(c);
            }
        return  "Cooperation created";
    }


    public Set<MinUserDTO>getCooperationForMentor(String email){

        User user = userRepository.findUserByEmail(email);

        Set<MinUserDTO> s =new HashSet<>();

        for(Cooperation c :cooperationRepository.findByMentors(user.getId(),CoopStatus.CREATED)){
            MinUserDTO dto = new MinUserDTO();
            dto.setId(c.getMentees().getId());
            dto.setName(c.getMentees().getAccounts().getUser().getFirst_name());
            dto.setSecondName(c.getMentees().getAccounts().getUser().getLast_name());
            dto.setAvatar(c.getMentees().getAccounts().getUser().getAvatar());
            s.add(dto);
        }
            return s;
    }

    public Set<DTOstatusCoopMentee> getCooperationForMentee(String email){

        User user = userRepository.findUserByEmail(email);

        Set<DTOstatusCoopMentee> s =new HashSet<>();

        Set<CoopStatus> coop = new HashSet<>();
        coop.add(CoopStatus.APPROVED);
        coop.add(CoopStatus.REJECTED);

        for(Cooperation c :cooperationRepository.findByMentees(user.getId(),coop)){
            MinUserDTO dtoUser = new MinUserDTO();

            dtoUser.setId(c.getMentors().getId());
            dtoUser.setName(c.getMentors().getAccounts().getUser().getFirst_name());
            dtoUser.setSecondName(c.getMentors().getAccounts().getUser().getLast_name());
            dtoUser.setAvatar(c.getMentees().getAccounts().getUser().getAvatar());

            DTOstatusCoopMentee dto =new DTOstatusCoopMentee();
            dto.setMentor(dtoUser);
            dto.setCoopStatus(c.getStatus());

            s.add(dto);
        }
        return s;

    }



    public Boolean decisionsOnCoop(String email, int id, StatusBoolDTO s){
        Mentors mentor =mentorRepository.getById(userRepository.findUserByEmail(email).getId());
        Mentees mentee =menteeRepository.getById(id);
        Cooperation cooperation = cooperationRepository.coopIsPresent(mentee.getId(),mentor.getId());
        if(cooperation!=null){

            if(s.getStatus()){
                cooperation.setStatus(CoopStatus.APPROVED);
            }
            else {
                cooperation.setStatus(CoopStatus.REJECTED);

            }
            cooperationRepository.save(cooperation);
            return true;

        }
        else  throw  new RuntimeException("Cooperation not found");
    }
    public Boolean responseMentee(String email,int mentorId){
        Mentees mentee =menteeRepository.getById(userRepository.findUserByEmail(email).getId());
        Cooperation cooperation = cooperationRepository.coopIsPresent(mentee.getId(),mentorId);

        if(cooperation == null) throw new RuntimeException("this coop does not exist");

        if(cooperation.getStatus() == CoopStatus.APPROVED)
            cooperation.setStatus(CoopStatus.STARTED);
        else if(cooperation.getStatus() == CoopStatus.REJECTED)
            cooperation.setStatus(CoopStatus.FROZEN);

        cooperationRepository.save(cooperation);
        return true;
    }
    public Boolean showInformation(String email,int mentorId){
        Mentees mentee =menteeRepository.getById(userRepository.findUserByEmail(email).getId());

        return (checkInfo(mentee.getId(),mentorId));


    }
    public Boolean checkInfo(int menteeId,int mentorId){
;
        Cooperation cooperation = cooperationRepository.coopIsPresent(menteeId,mentorId);

        if(cooperation == null)  return false;
        if(cooperation.getStatus() == CoopStatus.APPROVED||cooperation.getStatus() == CoopStatus.STARTED)
            return true;
        else return false;

    }
}
