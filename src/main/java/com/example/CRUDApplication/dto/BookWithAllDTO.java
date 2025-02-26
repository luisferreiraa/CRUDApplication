package com.example.CRUDApplication.dto;

import com.example.CRUDApplication.model.Book;

import java.util.List;
import java.util.stream.Collectors;

public class BookWithAllDTO {
    private Long id;
    private String title;
    private PublisherDTO publisher;
    private List<CategoryDTO> categories;
    private List<ReviewDTO> reviews;
    private List<AuthorDTO> authors;
    private Integer copies;
    private List<BorrowedBookDTO> borrowedBy;

    // Construtor que transforma um Book em BookWithAllDTO
    public BookWithAllDTO(Book book) {
        this.id = book.getId();
        this.title = book.getTitle();
        this.publisher = (book.getPublisher() != null) ? new PublisherDTO(book.getPublisher()) : null;

        // Converte listas de entidades para listas de DTOs
        this.authors = book.getAuthors().stream()
                .map(AuthorDTO::new)
                .collect(Collectors.toList());

        this.categories = book.getCategories().stream()
                .map(CategoryDTO::new)
                .collect(Collectors.toList());

        this.reviews = book.getReviews().stream()
                .map(ReviewDTO::new)
                .collect(Collectors.toList());

        this.copies = book.getCopies();

        // Mapeando BorrowedBook para BorrowedBookDTO, para incluir dados adicionais como data de empréstimo
        this.borrowedBy = book.getBorrowedBooks().stream()
                .map(borrowedBook -> new BorrowedBookDTO(borrowedBook.getUser().getId(), borrowedBook.getBook().getId(), borrowedBook.getBorrowDate()))
                .collect(Collectors.toList());
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

    public PublisherDTO getPublisher() {
        return publisher;
    }

    public void setPublisher(PublisherDTO publisher) {
        this.publisher = publisher;
    }

    public List<CategoryDTO> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryDTO> categories) {
        this.categories = categories;
    }

    public List<ReviewDTO> getReviews() {
        return reviews;
    }

    public void setReviews(List<ReviewDTO> reviews) {
        this.reviews = reviews;
    }

    public List<AuthorDTO> getAuthors() {
        return authors;
    }

    public void setAuthors(List<AuthorDTO> authors) {
        this.authors = authors;
    }

    public Integer getCopies() {
        return copies;
    }

    public void setCopies(Integer copies) {
        this.copies = copies;
    }

    public List<BorrowedBookDTO> getBorrowedBy() {
        return borrowedBy;
    }

    public void setBorrowedBy(List<BorrowedBookDTO> borrowedBy) {
        this.borrowedBy = borrowedBy;
    }
}
