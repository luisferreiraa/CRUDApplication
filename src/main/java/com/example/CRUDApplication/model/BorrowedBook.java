package com.example.CRUDApplication.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "borrowed_books")
public class BorrowedBook {

    @EmbeddedId
    private BorrowedBookId id = new BorrowedBookId();

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("bookId")
    @JoinColumn(name = "book_id")
    private Book book;

    @Column(name = "borrow_date", nullable = false)
    private LocalDate borrowDate;

    public BorrowedBook() {}

    public BorrowedBook(User user, Book book, LocalDate borrowDate) {
        this.user = user;
        this.book = book;
        this.borrowDate = borrowDate;
        this.id = new BorrowedBookId(user.getId(), book.getId());
    }

    // Getters e Setters

    public BorrowedBookId getId() {
        return id;
    }

    public void setId(BorrowedBookId id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }
}
