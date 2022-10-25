package com.mentor4you.model.DTO;

public class EmailRequest {
    private int id;
    private String email;

    public EmailRequest() {
    }

    public EmailRequest(int id, String email) {
        this.id = id;
        this.email = email;
    }

    // getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
