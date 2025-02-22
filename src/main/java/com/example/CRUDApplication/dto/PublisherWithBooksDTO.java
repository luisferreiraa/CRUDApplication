package com.example.CRUDApplication.dto;

import com.example.CRUDApplication.model.Publisher;

import java.util.List;
import java.util.stream.Collectors;

public class PublisherWithBooksDTO {
    private Long id;
    private String name;
    private List<BookWithAllDTO> books;

    public PublisherWithBooksDTO(Publisher publisher) {
        this.id = publisher.getId();
        this.name = publisher.getName();
        this.books = publisher.getBooks().stream()
                .map(BookWithAllDTO::new).
                collect(Collectors.toList());
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

    public List<BookWithAllDTO> getBooks() {
        return books;
    }

    public void setBooks(List<BookWithAllDTO> books) {
        this.books = books;
    }
}
