package com.example.CRUDApplication.dto;

import com.example.CRUDApplication.model.Book;

public class BookDTO {
    private Long id;
    private String title;
    private String author;
    private PublisherDTO publisher;

    public BookDTO(Book book) {
        this.id = book.getId();
        this.title = book.getTitle();
        this.author = book.getAuthors().toString();
        this.publisher = new PublisherDTO(book.getPublisher());
    }

    // Getters e setters
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public PublisherDTO getPublisher() {
        return publisher;
    }

    public void setPublisher(PublisherDTO publisher) {
        this.publisher = publisher;
    }
}
