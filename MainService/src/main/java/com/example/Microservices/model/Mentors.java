package com.example.Microservices.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@JsonIgnoreProperties({"hibernateLazyInitializer"})
@Entity
@Table(name = "Mentors")
public class Mentors {
    @Id
    private int id;

    private String description;

    private boolean showable_status;


    @OneToOne
    @JoinColumn(name = "id")
    @MapsId
    private Accounts accounts;

    @Column(name = "rating")
    private double rating;

    public Mentors() {
    }

    public Mentors(Accounts accounts,
                   String description,
                   int rating
    ) {
        this.accounts = accounts;
        this.description = description;
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public Accounts getAccounts() {
        return accounts;
    }

    public void setAccounts(Accounts accounts) {
        this.accounts = accounts;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mentors mentors = (Mentors) o;
        return id == mentors.id
                && showable_status == mentors.showable_status;
    }

}