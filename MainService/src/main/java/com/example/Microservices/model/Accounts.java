package com.example.Microservices.model;


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

    public Accounts() {
    }

    public Accounts(User user) {
        this.user = user;

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

}
