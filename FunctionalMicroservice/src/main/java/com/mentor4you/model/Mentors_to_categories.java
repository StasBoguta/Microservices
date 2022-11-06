package com.mentor4you.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Objects;


@Entity
@Table(name = "Mentors_to_categories")
public class Mentors_to_categories {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "categories_id")
    private Categories categories;

    @ManyToOne
    @JoinColumn(name = "mentors_id")
    @JsonIgnore
    private Mentors mentors;

    private int rate;

    private String currency;


    public Mentors_to_categories() {
    }

    public Mentors_to_categories(Categories categories,
                                 Mentors mentors,
                                 int rate,
                                 String currency) {
        this.categories = categories;
        this.mentors = mentors;
        this.rate = rate;
        this.currency = currency;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Categories getCategories() {
        return categories;
    }

    public void setCategories(Categories categories) {
        this.categories = categories;
    }

    public void setMentors(Mentors mentors) {
        this.mentors = mentors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mentors_to_categories that = (Mentors_to_categories) o;
        return rate == that.rate && categories.equals(that.categories) && currency.equals(that.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(categories, rate, currency);
    }

    @Override
    public String toString() {
        return "Mentors_to_categories{" +
                "id=" + id +
                ", rate=" + rate +
                ", currency='" + currency + '\'' +
                '}';
    }
}