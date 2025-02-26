package com.example.CRUDApplication.model;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class BorrowedBookId implements Serializable {

    private Long userId;
    private Long bookId;

    public BorrowedBookId() {}

    public BorrowedBookId(Long userId, Long bookId) {
        this.userId = userId;
        this.bookId = bookId;
    }

    // Getters e Setters

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }
}
