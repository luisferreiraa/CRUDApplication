package com.example.CRUDApplication.dto;

import com.example.CRUDApplication.model.Review;

public class ReviewDTO {
    private Long id;
    private String comment;
    private Integer rating;
    private Long bookId;
    private Long userId;
    private String username;

    // Construtor vazio necessário para serialização
    public ReviewDTO() {
    }

    public ReviewDTO(Review review) {
        if (review != null) {
            this.id = review.getId();
            this.comment = review.getComment();
            this.rating = review.getRating();
            this.bookId = (review.getBook() != null) ? review.getBook().getId() : null;
            this.userId = (review.getUser() != null) ? review.getUser().getId() : null;
            this.username = (review.getUser() != null) ? review.getUser().getUsername() : null;
        }
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
