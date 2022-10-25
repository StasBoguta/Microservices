package com.mentor4you.controller;

import com.mentor4you.model.*;
import com.mentor4you.repository.*;
import com.mentor4you.security.jwt.JwtProvider;
import com.mentor4you.service.EmailService;
import com.mentor4you.service.PasswordService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Random;


@RestController
@RequestMapping("/system")
public class SystemController {

    @Autowired
    private final AccountRepository accountRepository;
    private final MentorRepository mentorRepository;
    private final LanguagesRepository languagesRepository;
    private final MenteeRepository menteeRepository;
    private final CategoriesRepository categoriesRepository;
    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;
    private final PasswordService passwordService;
    private final ContactsToAccountsRepository contactsToAccountsRepository;
    private final TypeContactsRepository typeContactsRepository;
    private final EmailService emailService;

    public SystemController(
                            AccountRepository accountRepository,
                            MentorRepository mentorRepository,
                            LanguagesRepository languagesRepository,
                            MenteeRepository menteeRepository,
                            CategoriesRepository categoriesRepository,
                            JwtProvider jwtProvider,
                            UserRepository userRepository,
                            PasswordService passwordService,
                            ContactsToAccountsRepository contactsToAccountsRepository,
                            TypeContactsRepository typeContactsRepository,
                            EmailService emailService) {
        this.accountRepository = accountRepository;
        this.mentorRepository = mentorRepository;
        this.languagesRepository = languagesRepository;
        this.menteeRepository = menteeRepository;
        this.categoriesRepository = categoriesRepository;
        this.jwtProvider = jwtProvider;
        this.userRepository = userRepository;
        this.passwordService = passwordService;
        this.contactsToAccountsRepository = contactsToAccountsRepository;
        this.typeContactsRepository = typeContactsRepository;
        this.emailService = emailService;
    }

    @Operation(summary = "method add 1 admin, 3 moderators and 15 Mentors on you DB")
    @GetMapping("/add")
    //TODO delete after tests

    public String registerRoles() {

       try {



            createLanguages();
            createCategories();
            createSocialNetworks();

            int NUMBER_ADMINS = 1;
            int NUMBER_MODERATORS = 3;
            int NUMBER_MENTORS = 15;
            int NUMBER_MENTEES = 15;

            createAdmin(NUMBER_ADMINS);
            createModerators(NUMBER_MODERATORS);
            createMentors(NUMBER_MENTORS);
            createMentees(NUMBER_MENTEES);

            //connects mentors with social networks
            setTypeContactsMentor_Test();


            return "tables added";
        } catch (Exception ex) {
            return "The DataBase might have a set of test values, and method  " +
                    "\"GET http://localhost:8080/system/add\" " +
                    "was already called   \n \n Error:   " + ex.getMessage();
        }
    }


    //Create admin
    private void createAdmin(int numberAdmins) {
        for (int i = 1; i <= numberAdmins; i++) {

            User user = createOneUser(i, Role.ADMIN);
            accountRepository.save(createOneAccount(user, i));
        }
    }

    //Create moderators
    private void createModerators(int numberModerators) {
        for (int i = 1; i <= numberModerators; i++) {

            User user = createOneUser(i, Role.MODERATOR);
            accountRepository.save(createOneAccount(user, i));
        }

    }

    //CreateLanguages
    private void createLanguages() {

        languagesRepository.save(new Languages("ukrainian"));
        languagesRepository.save(new Languages("english"));
        languagesRepository.save(new Languages("russian"));
        languagesRepository.save(new Languages("polish"));
        languagesRepository.save(new Languages("сzech"));

    }

    private void createCategories() {

        categoriesRepository.save(new Categories("dhtm"));


    }


    private void createMentors(int numberOfMentors) {
        for (int i = 1; i <= numberOfMentors; i++) {

            User user = createOneUser(i, Role.MENTOR);

            Mentors m = new Mentors();
            m.setAccounts(createOneAccount(user, i));
            m.setDescription("description");
            m.setShowable_status(true);
            m.setGroupServ(GroupServ.MIX);
            m.isOnline();
            m.isOfflineIn();
            m.isOfflineOut();
            m.setEducations(Arrays.asList(new Educations(i + "edu"), new Educations(i + "edu_other")));
            m.setCertificats(Arrays.asList(new Certificats(i + "cert"), new Certificats(i + "cert_other")));

            mentorRepository.save(m);
        }
    }

    private void createMentees(int numberOfMentees) {
        for (int i = 1; i <= numberOfMentees; i++) {

            User user = createOneUser(i, Role.MENTEE);
            Mentees m = new Mentees();
            m.setAccounts(createOneAccount(user, i));

            menteeRepository.save(m);
        }
    }

    private Accounts createOneAccount(User user, int i) {

        Accounts a = new Accounts();

        a.setUser(user);
        a.setLast_visit(LocalDateTime.now());

        return a;
    }

    private User createOneUser(int i, Role role) {

        User n = new User();
        n.setEmail(i + "_" + role.name() + "@email.com");
        n.setPassword(passwordService.encodePassword("password"));
        n.setFirst_name(i + "_" + role.name() + "FN");
        n.setLast_name(i + "_" + role.name() + "LN");
        n.setRegistration_date(LocalDateTime.now());
        n.setStatus(true);
        n.setBan(false);
        n.setRole(role);

        return n;
    }

    //CreateSocialNetworks
    private void createSocialNetworks() {
        String[] arrSocNet = new String[]{
                "PhoneNumFirst",
                "PhoneNumSecond",
                "LinkedIn",
                "FaceBook",
                "Telegram",
                "Skype",
                "GitHub"
        };

        for (String socNetName : arrSocNet) {
            TypeContacts social_networks = new TypeContacts();
            social_networks.setName(socNetName);

            typeContactsRepository.save(social_networks);
        }
    }

    private void setTypeContactsMentor_Test() {
        // check exist users with Role.MENTOR
        int theMentors = accountRepository.findByRole(Role.MENTOR).size();

        // find all users with Role.MENTOR
        if (theMentors != 0) {
            List<Accounts> allMentor = accountRepository.findByRole(Role.MENTOR);
            int linkId = 3; // id=1  -->  LinkedIn

            for (Accounts accounts : allMentor) {
                int accountId = accounts.getId();

                ContactsToAccounts contactsToAccounts = new ContactsToAccounts();

                contactsToAccounts.setTypeContacts(typeContactsRepository.getById(linkId));
                contactsToAccounts.setAccounts(accounts);
                contactsToAccounts.setContactData((typeContactsRepository.findById(linkId).
                        get().getName()) + "_" + Role.MENTOR.name()
                        + "_Id_" + accountId);

                contactsToAccountsRepository.save(contactsToAccounts);
            }

            linkId = 1; // id=1  -->  PhoneNumber
            for (Accounts accounts : allMentor) {
                int accountId = accounts.getId();

                ContactsToAccounts contactsToAccounts = new ContactsToAccounts();

                contactsToAccounts.setTypeContacts(typeContactsRepository.getById(linkId));
                contactsToAccounts.setAccounts(accounts);
                contactsToAccounts.setContactData("(" + accountId + accountId + ")" + accountId + accountId);

                contactsToAccountsRepository.save(contactsToAccounts);
            }
        }
    }


    //added languages into Account
    @GetMapping("/addLanguages")
    private String addLanguages() {
        Random random = new Random();
        Languages languages = languagesRepository.getById(1);

        List<Accounts> list = accountRepository.findAll();
        list.forEach(a -> {
                    a.addLanguages(languages);
                    a.addLanguages(languagesRepository.getById(random.nextInt(4) + 2));
                    accountRepository.saveAndFlush(a);
                }
        );
        return "languages added";
    }


    @GetMapping("/testAuth")
    public String getUser() {
        return "hi authentificaters";
    }


    @Operation(summary = "send test email")
    @GetMapping("/sendTestEmail/{sendTo}")
    public String sendEmail(@PathVariable(value = "sendTo") String sendTo) throws MessagingException {
        return emailService.sendEmailRandomCode(sendTo,"", "45432");
    }
}
