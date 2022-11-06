package com.mentor4you.model.DTO;

public class ModeratorResponseDTO {
    private String lastName;
    private String firstName;
    private String email;

    public ModeratorResponseDTO() {
    }

    public ModeratorResponseDTO(String lastName, String firstName, String email) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getEmail() {
        return email;
    }
}
