package com.example.CRUDApplication.dto;

import com.example.CRUDApplication.model.Category;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CategoryNameDTO {
    @JsonProperty
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
