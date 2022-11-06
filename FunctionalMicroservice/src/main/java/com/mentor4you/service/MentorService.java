package com.mentor4you.service;

import com.mentor4you.exception.MentorNotFoundException;
import com.mentor4you.model.*;
import com.mentor4you.model.DTO.MenteeResponseDTO;
import com.mentor4you.model.DTO.mentorsExtendedInfo.MentorGeneralResponseDTO;
import com.mentor4you.model.DTO.mentorsExtendedInfo.MentorGeneralResponseIdDTO;
import com.mentor4you.repository.*;
import com.mentor4you.security.jwt.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Transactional
@Service
public class MentorService {

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


    public MentorService(
                         LanguagesService languagesService,
                         MentorsToCategory mentorsToCategory,
                         AccountRepository accountRepository,
                         MentorRepository mentorRepository,
                         UserRepository userRepository,
                         MenteeService menteeService,
                         UserService userService,
                         JwtProvider jwtProvider) {
        this.languagesService = languagesService;
        this.mentorsToCategory = mentorsToCategory;
        this.accountRepository = accountRepository;
        this.mentorRepository = mentorRepository;
        this.userRepository = userRepository;
        this.menteeService = menteeService;
        this.userService = userService;
        this.jwtProvider = jwtProvider;
    }

    //select all mentor
    public List<Mentors> getFullInfoAllMentors(){
        int theMentors = accountRepository.findByRole(Role.MENTOR).size();
        if(theMentors!=0){
            return mentorRepository.findAll().stream().filter(mentors -> mentors.getAccounts().getUser().getStatus()).collect(Collectors.toList());
        }
        throw new MentorNotFoundException("Mentors not found");

    }

    //    select mentor by id
    public ResponseEntity<MentorGeneralResponseIdDTO> getMentorById(int id){

        Mentors mentor = mentorRepository.findOneById(id);
        if(mentor!=null){
            MentorGeneralResponseIdDTO dto = new MentorGeneralResponseIdDTO();
            MenteeResponseDTO mDTO = userService.getOneMentee(mentor.getAccounts().getUser()).getBody();

            dto.setId(mentor.getId());
            dto.setAccountInfo(mDTO);
            dto.setOfflineIn(mentor.isOfflineIn());
            dto.setOfflineOut(mentor.isOfflineOut());
            dto.setOnline(mentor.isOnline());
            dto.setCategoriesList(mentor.getMentors_to_categories());
            dto.setDescription(mentor.getDescription());
            dto.setEducations(mentor.getEducations());
            dto.setCertificates(mentor.getCertificats());
            dto.setGroupServ(mentor.getGroupServ());
            dto.setRating(mentor.getRating());
            Set<String>l =new HashSet<>();
            for (Languages language : mentor.getAccounts().getLanguagesList()) {
                l.add(language.getName());
            }
            dto.setLanguages(l);
            Set<City>cities = new HashSet();
            for (CityToMentors n : mentor.getCityToMentors()){
                cities.add(n.getCity());
            }
            dto.setCities(cities);

            return new ResponseEntity<MentorGeneralResponseIdDTO>(dto,HttpStatus.OK);
        }
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    public ResponseEntity<MentorGeneralResponseDTO> getOneMentorByToken(HttpServletRequest request){
        String token = jwtProvider.getTokenFromRequest(request);

        String email = jwtProvider.getLoginFromToken(token);

        User user = userRepository.findUserByEmail(email);

        MenteeResponseDTO mDTO = userService.getOneMentee(user).getBody();

        try{

            Mentors m = mentorRepository.getById(user.getId());

            Set<String>l =new HashSet<>();

            for (Languages language : m.getAccounts().getLanguagesList()) {
                l.add(language.getName());
            }

            MentorGeneralResponseDTO dto =new MentorGeneralResponseDTO();
            dto.setAccountInfo(mDTO);
            dto.setCertificates(m.getCertificats());
            dto.setEducations(m.getEducations());
            dto.setDescription(m.getDescription());
            dto.setCategoriesList(m.getMentors_to_categories());
            dto.setOnline(m.isOnline());
            dto.setOfflineIn(m.isOfflineIn());
            dto.setOfflineOut(m.isOfflineOut());
            dto.setShowable_status(m.isShowable_status());
            dto.setLanguages(l);
            dto.setGroupServ(m.getGroupServ());
            dto.setRating(m.getRating());

            Set<City>cities = new HashSet();
            for (CityToMentors n : m.getCityToMentors()){
                cities.add(n.getCity());
            }
            dto.setCities(cities);

            return new ResponseEntity<MentorGeneralResponseDTO>(dto, HttpStatus.OK);
        }
        catch (EntityNotFoundException e)
        {
            return new ResponseEntity<MentorGeneralResponseDTO>(HttpStatus.NOT_FOUND);
        }

    }

    public ResponseEntity<String> updateMentorByToken(MentorGeneralResponseDTO dto,
                                                                     HttpServletRequest request) {

        String token = jwtProvider.getTokenFromRequest(request);

        String email = jwtProvider.getLoginFromToken(token);

        User user = userRepository.findUserByEmail(email);
        if(user.getRole() ==Role.MENTOR && user!=null) {
        userService.updateUser(user,dto.getAccountInfo());
        Mentors mentor = mentorRepository.getById(user.getId());
        cityToMentorRepository.deleteC(mentor);


        remove(mentor);
        for (Mentors_to_categories n : dto.getCategoriesList()){
                n.setMentors(mentor);
        }

        Set<Languages>l =new HashSet<>();
        if(dto.getLanguages()!=null){
            l = languagesService.getAllLanguages(dto.getLanguages());
        }
        Set<CityToMentors> cityToMentors =new HashSet<>();
        for(City city:dto.getCities()){
            cityToMentors.add(new CityToMentors(city,mentor));
        }


        mentor.setMentors_to_categories(dto.getCategoriesList());
        mentor.setCertificats(dto.getCertificates());
        mentor.setEducations(dto.getEducations());
        mentor.setShowable_status(dto.isShowable_status());
        mentor.setOnline(dto.isOnline());
        mentor.setOfflineOut(dto.isOfflineOut());
        mentor.setOfflineIn(dto.isOfflineIn());
        mentor.getAccounts().setLanguagesList(l);
        mentor.setDescription(dto.getDescription());
        mentor.setGroupServ(dto.getGroupServ());
        mentor.setRating(dto.getRating());
        mentor.setCityToMentors(cityToMentors);

        mentorRepository.save(mentor);
        return new ResponseEntity<String>(HttpStatus.OK);
        }
        else return  new ResponseEntity<String>(HttpStatus.NOT_FOUND);

    }

      public void remove(Mentors m){
        if(m.getMentors_to_categories()!=null)
            mentorsToCategory.removeByMentors(m);
    }
}
