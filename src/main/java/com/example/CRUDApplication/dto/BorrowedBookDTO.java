package com.example.CRUDApplication.dto;

import java.time.LocalDate;

public class BorrowedBookDTO {
    private Long userId;
    private Long bookId;
    private LocalDate borrowedDate;

    // Construtores
    public BorrowedBookDTO(Long userId, Long bookId, LocalDate borrowedDate) {
        this.userId = userId;
        this.bookId = bookId;
        this.borrowedDate = borrowedDate;
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

    public LocalDate getBorrowedDate() {
        return borrowedDate;
    }

    public void setBorrowedDate(LocalDate borrowedDate) {
        this.borrowedDate = borrowedDate;
    }
}

