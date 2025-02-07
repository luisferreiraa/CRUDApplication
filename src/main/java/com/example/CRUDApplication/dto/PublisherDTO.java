package com.example.CRUDApplication.dto;

import com.example.CRUDApplication.model.Publisher;

public class PublisherDTO {
    private Long id;
    private String name;

    public PublisherDTO(Publisher publisher) {
        this.id = publisher.getId();
        this.name = publisher.getName();
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
