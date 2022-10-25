package com.mentor4you.model;

import javax.persistence.*;

@Entity
@Table(name = "SystemEmails")
public class SystemEmails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String value;

    private String owner;

    public SystemEmails() {
    }

    public SystemEmails(int id, String value, String owner) {
        this.id = id;
        this.value = value;
        this.owner = owner;
    }

    public int getId() {
        return id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

}
