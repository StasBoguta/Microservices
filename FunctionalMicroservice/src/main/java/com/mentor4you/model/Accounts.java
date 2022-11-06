package com.mentor4you.model;


import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="Accounts")
public class Accounts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "last_visit")
    private LocalDateTime last_visit;

    @OneToOne
    @JoinColumn(name = "id")
    @MapsId
    private User user;

    @OneToOne (mappedBy = "accounts")
    private Mentors mentors;

    @OneToOne (mappedBy = "accounts")
    private Mentees mentees;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "languages_to_accounts",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "languages_id")
    )
    private Set<Languages> languagesList;


    @OneToMany(mappedBy = "accounts")
    private Set<ContactsToAccounts> contactsToAccounts;


    public Accounts() {
    }

    public Accounts(User user,
                    LocalDateTime last_visit) {
        this.user = user;
        this.last_visit = last_visit;
    }


    public int getId() {return id;}

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getLast_visit() {
        return last_visit;
    }

    public void setLast_visit(LocalDateTime last_visit) {
        this.last_visit = last_visit;
    }

    public Set<Languages> getLanguagesList() {
        return languagesList;
    }

    public void addLanguages(Languages languages) {
        if(languagesList.isEmpty()) languagesList = new HashSet<>();
        this.languagesList.add(languages);
        languages.addAccounts(this);
    }

    public void setLanguagesList(Set<Languages> languagesList) {
        this.languagesList = languagesList;
    }

    public Set<ContactsToAccounts> getContactsToAccounts() {
        return contactsToAccounts;
    }

    public void setContactsToAccounts(Set<ContactsToAccounts> contactsToAccounts) {
        this.contactsToAccounts = contactsToAccounts;
    }
}
