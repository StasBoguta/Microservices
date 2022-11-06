package com.mentor4you.model.DTO.mentorsExtendedInfo;


import com.mentor4you.model.*;
import com.mentor4you.model.DTO.MenteeResponseDTO;

import java.util.List;
import java.util.Set;

public class MentorGeneralResponseDTO {

    private MenteeResponseDTO accountInfo;

    private String description;
    private boolean showable_status;
    private boolean isOnline;
    private boolean isOfflineIn;
    private boolean isOfflineOut;

    private GroupServ groupServ;

    private double rating;

    private List<Educations> educations;

    private List<Certificats> certificates;

    private Set<Mentors_to_categories> categoriesList;

    private Set<String> languages;

    private Set<City> cities;


    public MentorGeneralResponseDTO() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isShowable_status() {
        return showable_status;
    }

    public void setShowable_status(boolean showable_status) {
        this.showable_status = showable_status;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    public boolean isOfflineIn() {
        return isOfflineIn;
    }

    public void setOfflineIn(boolean offlineIn) {
        isOfflineIn = offlineIn;
    }

    public boolean isOfflineOut() {
        return isOfflineOut;
    }

    public void setOfflineOut(boolean offlineOut) {
        isOfflineOut = offlineOut;
    }

    public List<Educations> getEducations() {
        return educations;
    }

    public void setEducations(List<Educations> educations) {
        this.educations = educations;
    }

    public List<Certificats> getCertificates() {
        return certificates;
    }

    public void setCertificates(List<Certificats> certificats) {
        this.certificates = certificats;
    }

    public Set<Mentors_to_categories> getCategoriesList() {return categoriesList;}

    public void setCategoriesList(Set<Mentors_to_categories> categoriesList) {
        this.categoriesList = categoriesList;
    }

    public MenteeResponseDTO getAccountInfo() {
        return accountInfo;
    }

    public void setAccountInfo(MenteeResponseDTO accountInfo) {
        this.accountInfo = accountInfo;
    }

    public Set<String> getLanguages() {
        return languages;
    }

    public void setLanguages(Set<String> languages) {
        this.languages = languages;
    }

    public GroupServ getGroupServ() {
        return groupServ;
    }

    public void setGroupServ(GroupServ groupServ) {
        this.groupServ = groupServ;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public Set<City> getCities() {
        return cities;
    }

    public void setCities(Set<City> cities) {
        this.cities = cities;
    }
}