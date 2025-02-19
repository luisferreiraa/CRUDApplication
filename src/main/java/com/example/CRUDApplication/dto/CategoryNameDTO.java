package com.example.CRUDApplication.dto;

import com.example.CRUDApplication.model.Category;

public class CategoryNameDTO {

    private String name;

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
