package com.mentor4you.model;

import com.mentor4you.model.DTO.coopDTO.CoopStatus;

import javax.persistence.*;

@Entity
@Table(name = "Cooperation")
public class Cooperation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "mentees_id")
    private Mentees mentees;

    @ManyToOne
    @JoinColumn(name = "mentor_id")
    private Mentors mentors;

    @Enumerated(EnumType.STRING)
    private CoopStatus status;


    public Cooperation() {
    }


    public Mentees getMentees() {
        return mentees;
    }

    public void setMentees(Mentees mentees) {
        this.mentees = mentees;
    }

    public Mentors getMentors() {
        return mentors;
    }

    public void setMentors(Mentors mentors) {
        this.mentors = mentors;
    }

    public CoopStatus getStatus() {
        return status;
    }

    public void setStatus(CoopStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Cooperation{" +
                "id=" + id +
                ", mentees=" + mentees +
                ", mentors=" + mentors +
                ", status=" + status +
                '}';
    }
}
