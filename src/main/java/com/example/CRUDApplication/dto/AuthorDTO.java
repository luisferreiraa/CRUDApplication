// dto/AuthorDTO

package com.example.CRUDApplication.dto;

import com.example.CRUDApplication.model.Author;

import java.util.List;
import java.util.stream.Collectors;

public class AuthorDTO {
    private Long id;
    private String name;
    private List<BookDTO> books;

    public AuthorDTO(Author author) {
        this.id = author.getId();
        this.name = author.getName();
        this.books = author.getBooks().stream()
                .map(BookDTO::new)  // Converte cada Book num BookDTO
                .collect(Collectors.toList());

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

    public List<BookDTO> getBooks() {
        return books;
    }

    public void setBooks(List<BookDTO> books) {
        this.books = books;
    }
}
