package com.example.CRUDApplication.dto;

import com.example.CRUDApplication.model.Book;

public class BookDTO {
    private Long id;
    private String title;

    public BookDTO(Book book) {
        this.id = book.getId();
        this.title = book.getTitle();
    }

    // Getters e Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
