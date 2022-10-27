package com.example.Microservices.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
public class User {

    private int id;
    private String role;
    private String email;
    private String password;
    private String first_name;
    private String last_name;
    private String avatar;
    private LocalDateTime registration_date;
    private Boolean status;
    private Boolean ban;
    private Integer accounts;

    public User() {
    }

    public User(String role,
                String email,
                String password,
                String first_name,
                String last_name,
                String avatar,
                LocalDateTime registration_date,
                Boolean status,
                Boolean ban,
                Integer accounts) {
        this.role = role;
        this.email = email;
        this.password = password;
        this.first_name = first_name;
        this.last_name = last_name;
        this.avatar = avatar;
        this.registration_date = registration_date;
        this.status = status;
        this.ban = ban;
        this.accounts = accounts;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id)  && Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", role=" + role +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", registration_date=" + registration_date +
                ", status=" + status +
                ", accounts=" + accounts +
                '}';
    }
}
