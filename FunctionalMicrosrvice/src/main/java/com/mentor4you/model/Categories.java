package com.mentor4you.model;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="Categories")
public class Categories {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @OneToMany (cascade = CascadeType.ALL,mappedBy = "categories")
    private List<Mentors_to_categories> mentors_to_categories;

    public Categories() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public Categories( String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Categories that = (Categories) o;
        return id == that.id && name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}