package com.mentor4you.model;

import javax.persistence.*;

@Entity
@Table(name = "Educations")
public class Educations{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String description;



    public Educations(){}

    public Educations(String name) {
        this.name = name;
    }

    public Educations(String name,String description) {
        this.name = name;
        this.description =description;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Educations{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
