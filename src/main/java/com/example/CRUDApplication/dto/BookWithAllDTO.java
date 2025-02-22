package com.example.CRUDApplication.dto;

import com.example.CRUDApplication.model.Book;

public class BookWithAllDTO {
    private Long id;
    private String title;
    private String author;
    private String publisherName;

    public BookWithAllDTO(Book book) {
        this.id = book.getId();
        this.title = book.getTitle();
        this.author = book.getAuthors().toString();
        this.publisherName = book.getPublisher().getName();
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

    public String getPublisher() {
        return publisherName;
    }

    public void setPublisher(PublisherWithBooksDTO publisher) {
        this.publisherName = publisher.getName();
    }
}
