package com.mentor4you.service;

import com.mentor4you.exception.AdminDeleteException;
import com.mentor4you.exception.MenteeNotFoundException;
import com.mentor4you.exception.RegistrationException;
import com.mentor4you.model.*;
import com.mentor4you.model.DTO.MenteeResponseDTO;
import com.mentor4you.model.DTO.UserBanDTO;
import com.mentor4you.repository.*;
import com.mentor4you.security.jwt.JwtProvider;
import com.mentor4you.security.jwt.cache.event.OnUserLogoutSuccessEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    ApplicationEventPublisher applicationEventPublisher;
    PasswordService passwordService;
    UserRepository userRepository;
    JwtProvider jwtProvider;
    AuthenticationService authenticationService;
    EmailService emailService;
    ContactsToAccountsService contactsToAccountsService;
    ContactsToAccountsRepository contactsToAccountsRepository;
    MentorRepository mentorRepository;
    MenteeRepository menteeRepository;
    AccountRepository accountRepository;

    public UserService(ApplicationEventPublisher applicationEventPublisher, PasswordService passwordService, UserRepository userRepository, JwtProvider jwtProvider, AuthenticationService authenticationService, EmailService emailService, ContactsToAccountsService contactsToAccountsService, ContactsToAccountsRepository contactsToAccountsRepository, MentorRepository mentorRepository, MenteeRepository menteeRepository, AccountRepository accountRepository) {
        this.applicationEventPublisher = applicationEventPublisher;
        this.passwordService = passwordService;
        this.userRepository = userRepository;
        this.jwtProvider = jwtProvider;
        this.authenticationService = authenticationService;
        this.emailService = emailService;
        this.contactsToAccountsService = contactsToAccountsService;
        this.contactsToAccountsRepository = contactsToAccountsRepository;
        this.mentorRepository = mentorRepository;
        this.menteeRepository = menteeRepository;
        this.accountRepository = accountRepository;
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public String changePassword(HttpServletRequest request, String oldPassword, String newPassword) {
        String token = jwtProvider.getTokenFromRequest(request);
        String email =jwtProvider.getLoginFromToken(token);

        User user = userRepository.findUserByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found ");
        }
        if(passwordService.equalsPassword(oldPassword, user.getPassword())){
            if(!passwordService.isValidPassword(newPassword)){
                throw new RegistrationException("Password is not valid");
            }
            user.setPassword(passwordService.encodePassword(newPassword));
            userRepository.save(user);
            return "Password changed";
        }else{
            throw new RegistrationException("The old password is incorrect");
        }
    }


    public List<UserBanDTO> getAllBannedUsers(Boolean bool){

        List<User> userslist = userRepository.findByBan(bool);
        List<UserBanDTO> userBanList = new ArrayList<>();

            for (User user : userslist) {
                UserBanDTO userBan = new UserBanDTO();
                userBan.setId(user.getId());
                userBan.setRole(user.getRole());
                userBan.setEmail(user.getEmail());
                userBan.setFirst_name(user.getFirst_name());
                userBan.setLast_name(user.getLast_name());
                userBan.setAvatar(user.getAvatar());
                userBan.setBan(user.getBan());

                userBanList.add(userBan);
            }
            return userBanList;
    }



    public String changeBanToUser(Boolean bool, int id){
        User user = userRepository.findOneById(id);
        if (user == null) {
            throw new UsernameNotFoundException("User not found ");
        }
        else {
            if(user.getBan()!=bool){user.setBan(bool);
                userRepository.save(user);
                return "User's ban status changed";}
            else { return "User ban status has not been changed";}
        }
    }
    public ResponseEntity<String> updateUser(User userToUpdate, MenteeResponseDTO request){
        String emailNew = request.getEmail();
        int id = userToUpdate.getId();

        if (userToUpdate != null) {

                if(request.getFirstName().isEmpty()){userToUpdate.setFirst_name("");}
                else{userToUpdate.setFirst_name(request.getFirstName());}

                if(request.getLastName().isEmpty()){userToUpdate.setLast_name("");}
                else{ userToUpdate.setLast_name(request.getLastName());}

                //update email using method from emailService
                //if emails are equals do nothing
                if (!userToUpdate.getEmail().equals(emailNew)) {
                    //TODO create new token with new email
                    String reportUpdate = emailService.updateEmail(emailNew, id);
                }
                userRepository.save(userToUpdate);

                contactsToAccountsService.changeContactsDataUser(request, id);

        } else {
            throw new MenteeNotFoundException("User with id = " + id + " not found");
        }
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    public ResponseEntity<MenteeResponseDTO> getOneMentee(User user){

        int id = user.getId();
            Map<String, String> socialMap = new HashMap<>();

            //Social_networks socialNetworks = socialNetworksRepository.getById(id);
            if (user != null) {
                List<ContactsToAccounts> listConToAkk = contactsToAccountsRepository.findAllByAccounts(id);

                if (listConToAkk.size() > 0) {
                    for (ContactsToAccounts lA : listConToAkk) {
                        String typContact = lA.getTypeContacts().getName();
                        String contData = lA.getContactData();
                        socialMap.put(typContact, contData);
                    }
                } else {
                    socialMap.put("", "");
                }
                MenteeResponseDTO mDTO = new MenteeResponseDTO();
                if(user.getFirst_name()==null){
                    mDTO.setFirstName("");}
                else{mDTO.setFirstName(user.getFirst_name());}
                if(user.getLast_name()==null)
                {mDTO.setLastName("");}
                else{mDTO.setLastName(user.getLast_name());}
                if(user.getAvatar()==null)
                {mDTO.setAvatar("https://awss3mentor4you.s3.eu-west-3.amazonaws.com/avatars/standartUserAvatar.png");}
                else{mDTO.setAvatar(user.getAvatar());}
                mDTO.setEmail(user.getEmail());
                mDTO.setSocialMap(socialMap);
                return new ResponseEntity<MenteeResponseDTO>(mDTO, HttpStatus.OK);
            }
            return null;
    }

    public String deleteUser(HttpServletRequest request) throws MessagingException {
        String token = jwtProvider.getTokenFromRequest(request);
        String email = jwtProvider.getLoginFromToken(token);
        User user = userRepository.findUserByEmail(email);

        if(user.getRole().name().equals("ADMIN")){
            throw new AdminDeleteException("You can not delete ADMIN account");
        }

        user.setStatus(false);
        userRepository.save(user);

//        emailService.sendNotificationToEmail(user.getEmail(),"Account has been deleted");

        OnUserLogoutSuccessEvent logoutEventPublisher = new OnUserLogoutSuccessEvent(user.getEmail(),token);
        applicationEventPublisher.publishEvent(logoutEventPublisher);

        return "Account has been deleted";
    }

    public String changeAvatar(String header, String avatarURL) throws Exception {
        User user = getUserByHeader(header);
        if (user == null) {
            throw new UsernameNotFoundException("User is not found.");
        }
        if(!(avatarURL.startsWith("http://") || avatarURL.startsWith("https://"))) {
            throw new Exception("New Avatar URL is incorrect");
        }
        user.setAvatar(avatarURL);
        userRepository.save(user);
        return "You avatar is changed";
    }

    public String changeMyRole(String header){
        try {
            User user = getUserByHeader(header);
            Accounts accounts = accountRepository.getById(user.getId());
            if (user.getRole().equals(Role.MENTOR)) {
                user.setRole(Role.MENTEE);
                if(!menteeRepository.existsById(user.getId())) {
                    Mentees mentee = new Mentees();
                    mentee.setAccounts(accounts);
                    menteeRepository.save(mentee);
                }
            } else if (user.getRole().equals(Role.MENTEE)) {
                user.setRole(Role.MENTOR);
                if(!mentorRepository.existsById(user.getId())) {
                    Mentors mentor = new Mentors();
                    mentor.setAccounts(accounts);
                    mentorRepository.save(mentor);
                }
            }
            userRepository.save(user);
            return "Congratulation, you are become a ".concat(user.getRole().name());
        } catch (Exception e){
            return e.getMessage();
        }
    }

    public int getIdByHeader(String header) throws Exception{
        return getUserByHeader(header).getId();
    }
    public User getUserByHeader(String header) throws Exception{
        String token;
        if(header.startsWith("Bearer ")){
            token = header.substring(7);
        } else {
            token = header;
        }
        if(!jwtProvider.validateToken(token)){
            throw new Exception("Your token is expired");
        }
        String email = jwtProvider.getLoginFromToken(token);
        return userRepository.findUserByEmail(email);
    }
    public String getAvatarByHeader (String header) throws Exception{
        return getUserByHeader(header).getAvatar();
    }

    public User getUserById(String id) {
        int userId = Integer.parseInt(id);
        return userRepository.findOneById(userId);
    }

    public String getAvatarByToken (String token) throws Exception{
        String email = jwtProvider.getLoginFromToken(token);
        User user = userRepository.findUserByEmail(email);
        return user.getAvatar();
    }
}
