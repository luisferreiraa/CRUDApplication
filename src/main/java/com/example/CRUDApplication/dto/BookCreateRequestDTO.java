package com.example.CRUDApplication.dto;

import com.example.CRUDApplication.model.Book;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class BookCreateRequestDTO {

    private Long id;
    @NotNull(message = "Book title cannot be null")
    @NotEmpty(message = "Book title cannot be empty")
    @NotBlank(message = "Book title cannot be blank")
    private String title;
    private List<Long> authorsId;
    private Long publisherId;
    private List<Long> categoriesId;

//    public BookCreateRequestDTO(Book book) {
//        this.id = book.getId();
//        this.title = book.getTitle();
//        this.authorsId = book.getAuthors();
//        this.publisherId = book.getPublisher();
//        this.categoriesId = book.getCategories();
//    }

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

    public List<Long> getAuthorsId() {
        return authorsId;
    }

    public void setAuthorsId(List<Long> authorsId) {
        this.authorsId = authorsId;
    }

    public Long getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(Long publisherId) {
        this.publisherId = publisherId;
    }

    public List<Long> getCategoriesId() {
        return categoriesId;
    }

    public void setCategoriesId(List<Long> categoriesId) {
        this.categoriesId = categoriesId;
    }
}
