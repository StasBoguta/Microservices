package com.mentor4you.model.DTO;


import com.mentor4you.model.Role;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class UserDTO {

    @NotNull
    @NotEmpty
    private final String email;

    @NotNull
    @NotEmpty
    private final String password;

    @NotNull
    @NotEmpty
    private final Role role;

    public UserDTO(String email, String password, Role role) {
        this.email = email;
        this.password = password;

        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole(){return role;}
}
