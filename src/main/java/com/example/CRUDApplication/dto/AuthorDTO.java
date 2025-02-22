package com.example.CRUDApplication.dto;

import com.example.CRUDApplication.model.Author;

public class AuthorDTO {
    private Long id;
    private String name;

    public AuthorDTO(Author author) {
        this.id = author.getId();
        this.name = author.getName();
    }

    // Getters e Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
