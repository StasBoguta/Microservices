package com.mentor4you.model.DTO.serchMentorsDTO;

import java.util.ArrayList;
import java.util.List;


public class SmallDataMentorDTO{


    private int id;
    private String firstName;
    private String lastName;
    private String avatar;
    private String foundCategory="";
    private int rate;
    private double rating;
    private String currency;
    private boolean isOnline;
    private List<String> categories = new ArrayList<>();


    public SmallDataMentorDTO() {
    }


    public SmallDataMentorDTO(int id,
                              String firstName,
                              String lastName,
                              String avatar,
                              String foundCategory,
                              int rate,
                              int rating,
                              String currency,
                              boolean isOnline,
                              List<String> categories) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.avatar = avatar;
        this.foundCategory = foundCategory;
        this.rate = rate;
        this.rating = rating;
        this.currency = currency;
        this.isOnline = isOnline;
        this.categories = categories;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getFoundCategory() {
        return foundCategory;
    }

    public void setFoundCategory(String foundCategory) {
        this.foundCategory = foundCategory;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }


}
