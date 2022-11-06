package com.mentor4you.model;

import javax.persistence.*;

@Entity
@Table(name = "Certificats")
public class Certificats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String description;

    private String link;



    public Certificats(){}

    public Certificats(String name) {
        this.name = name;
    }

    public Certificats(String name, String description,String link) {
        this.name = name;
        this.description =description;
        this.link=link;
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

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }


    @Override
    public String toString() {
        return "Certificats{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", link='" + link + '\'' +
                '}';
    }
}
