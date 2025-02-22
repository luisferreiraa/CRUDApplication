package com.example.CRUDApplication.dto;

import com.example.CRUDApplication.model.Category;

import java.util.List;
import java.util.stream.Collectors;

public class CategoryWithBooksDTO {
    private Long id;
    private String name;
    private List<BookWithAllDTO> books;

    public CategoryWithBooksDTO(Category category) {
        this.id = category.getId();
        this.name = category.getName();
        this.books = category.getBooks().stream()
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

