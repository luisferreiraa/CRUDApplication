package com.example.CRUDApplication.dto;

import com.example.CRUDApplication.model.Category;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class CategoryNameDTO {
    @JsonProperty
    @NotNull(message = "Category name cannot be null")
    @NotEmpty(message = "Category name cannot be empty")
    @NotBlank(message = "Category name cannot be blank")
    private String name;

    public CategoryNameDTO() {} // Construtor vazio é necessário para desserialização

    public String getName() {
        return name;
    }

    public CategoryNameDTO(Category category) {
        this.name = category.getName();
    }

    public void setName(String name) {
        this.name = name;
    }
}
