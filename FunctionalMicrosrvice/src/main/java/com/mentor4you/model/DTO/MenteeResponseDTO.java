package com.mentor4you.model.DTO;

import javax.validation.constraints.NotNull;
import java.util.Map;

public class MenteeResponseDTO {

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private String email;

    @NotNull
    private String avatar;

    @NotNull
    private Map<String, String> socialMap;
    //map(a->a.mapIncom)

    public MenteeResponseDTO() {
    }

    public MenteeResponseDTO( String firstName,String lastName,String email,String avatar,Map<String, String> socialMap) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.avatar = avatar;
        this.socialMap = socialMap;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Map<String, String> getSocialMap() {
        return socialMap;
    }

    public void setSocialMap(Map<String, String> socialMap) {
        this.socialMap = socialMap;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
