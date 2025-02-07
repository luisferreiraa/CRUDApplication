package com.example.CRUDApplication.dto;

import com.example.CRUDApplication.model.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserDTO {
    private Long id;
    private String username;
    private List<BookDTO> borrowedBooks;

    public UserDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.borrowedBooks = user.getBorrowedBooks().stream()
                .map(BookDTO::new)
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

    public List<BookDTO> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void setBorrowedBooks(List<BookDTO> borrowedBooks) {
        this.borrowedBooks = borrowedBooks;
    }
}
