package com.example.CRUDApplication.dto;

import com.example.CRUDApplication.model.Review;

public class ReviewDTO {
    private Long id;
    private String reviewerName;
    private String comment;
    private Integer rating;
    private BookDTO book;

    // Construtor vazio necessário para serialização
    public ReviewDTO() {

    }

    public ReviewDTO(Review review) {
        if (review != null) {
            this.id = review.getId();
            this.reviewerName = review.getReviewerName();
            this.comment = review.getComment();
            this.rating = review.getRating();
            this.book = (review.getBook() != null) ? new BookDTO(review.getBook()) : null;
        }
    }

    // Getters e Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReviewerName() {
        return reviewerName;
    }

    public void setReviewerName(String reviewerName) {
        this.reviewerName = reviewerName;
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

    public BookDTO getBook() {
        return book;
    }

    public void setBook(BookDTO book) {
        this.book = book;
    }
}
