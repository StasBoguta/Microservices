package com.mentor4you.model.DTO;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class UserDTO {

    @NotNull
    @NotEmpty
    private final String email;

    @NotNull
    @NotEmpty
    private final String password;



    public UserDTO(String email, String password) {
        this.email = email;
        this.password = password;

    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
