package com.mentor4you.model.DTO;

import com.mentor4you.model.Certificats;
import com.mentor4you.model.Educations;
import com.mentor4you.model.Mentors_to_categories;

import java.util.List;
import java.util.Set;

public class ExtendedMenteeDTO {

    private int id;

    private String name;

    private String secondName;

    private String description;

    private boolean isOnline;

    private boolean isOfflineIn;

    private boolean isOfflineOut;

    private List<Educations> educations;

    private List<Certificats> certificats;

    private Set<Mentors_to_categories> categories;

    private Set<String> languages;

    public ExtendedMenteeDTO() {
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public List<Certificats> getCertificats() {
        return certificats;
    }

    public void setCertificats(List<Certificats> certificats) {
        this.certificats = certificats;
    }

    public Set<Mentors_to_categories> getCategories() {
        return categories;
    }

    public void setCategories(Set<Mentors_to_categories> categories) {
        this.categories = categories;
    }

    public Set<String> getLanguages() {
        return languages;
    }

    public void setLanguages(Set<String> languages) {
        this.languages = languages;
    }
}
