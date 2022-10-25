package com.example.Microservices.model;

import javax.persistence.*;

@Entity
@Table(name = "Mentees")
public class Mentees {
    @Id
    private int id;


    @OneToOne
    @JoinColumn(name = "id")
    @MapsId
    private Accounts accounts;

    public Mentees() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Accounts getAccounts() {
        return accounts;
    }

    public void setAccounts(Accounts accounts) {
        this.accounts = accounts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mentees mentees = (Mentees) o;
        return id == mentees.id;
    }

    @Override
    public String toString() {
        return "Mentees{" +
                "id=" + id +
                ", accounts=" + accounts +
                '}';
    }
}