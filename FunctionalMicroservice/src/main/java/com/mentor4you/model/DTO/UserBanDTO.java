package com.mentor4you.model.DTO;

import com.mentor4you.model.Role;

public class UserBanDTO {
    private int id;
    private Role role;
    private String email;
    private String first_name;
    private String last_name;
    private String avatar;
    private Boolean ban;

    public UserBanDTO() {
    }

    public UserBanDTO(int id, Role role, String email, String first_name, String last_name, String avatar, Boolean ban) {
        this.id = id;
        this.role = role;
        this.email = email;
        this.first_name = first_name;
        this.last_name = last_name;
        this.avatar = avatar;
        this.ban = ban;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Boolean getBan() {
        return ban;
    }

    public void setBan(Boolean ban) {
        this.ban = ban;
    }
}
