package com.example.CRUDApplication.dto;

import com.example.CRUDApplication.model.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserWithBooksDTO {
    private Long id;
    private String username;
    private List<BorrowedBookDTO> borrowedBooks;

    public UserWithBooksDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.borrowedBooks = user.getBorrowedBooks().stream()
                .map(borrowedBook -> new BorrowedBookDTO(borrowedBook.getUser().getId(), borrowedBook.getBook().getId(), borrowedBook.getBorrowDate()))
                .collect(Collectors.toList());
    }

    // Getters e Setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<BorrowedBookDTO> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void setBorrowedBooks(List<BorrowedBookDTO> borrowedBooks) {
        this.borrowedBooks = borrowedBooks;
    }
}
