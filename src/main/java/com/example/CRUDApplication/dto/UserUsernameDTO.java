package com.example.CRUDApplication.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class UserUsernameDTO {

    @NotNull(message = "Username cannot be null")
    @NotEmpty(message = "Username cannot be empty")
    @NotBlank(message = "Username cannot be blank")
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String name) {
        this.username = name;
    }
}
