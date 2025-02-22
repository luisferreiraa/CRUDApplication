package com.example.CRUDApplication.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class PublisherNameDTO {

    @NotNull(message = "Publisher name cannot be null")
    @NotEmpty(message = "Publisher name cannot be empty")
    @NotBlank(message = "Publisher name cannot be blank")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
