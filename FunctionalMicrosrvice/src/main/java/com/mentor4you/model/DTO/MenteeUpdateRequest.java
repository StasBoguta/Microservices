package com.mentor4you.model.DTO;

import java.util.Map;

public class MenteeUpdateRequest {
/*
    ObjectMapper mapper = new ObjectMapper();
    Map<String,Object> map = mapper.readValue(json, Map.class);
    */

    private String firstName;
    private String lastName;
    private String email;

    private Map<String, String> socialMap;

    public MenteeUpdateRequest() {
    }

    public MenteeUpdateRequest(String firstName,
                               String lastName,
                               String email,
                               Map<String, String> socialMap) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.socialMap = socialMap;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public Map<String, String> getSocialMap() {
        return socialMap;
    }
}
