package com.example.CRUDApplication.dto;

import com.example.CRUDApplication.model.Review;

public class ReviewDTO {
    private Long id;
//    private String reviewerName;
    private String comment;
    private Integer rating;
    private BookWithAllDTO book;
    private UserWithBooksDTO user;

    // Construtor vazio necessário para serialização
    public ReviewDTO() {

    }

    public ReviewDTO(Review review) {
        if (review != null) {
            this.id = review.getId();
//            this.reviewerName = review.getReviewerName();
            this.comment = review.getComment();
            this.rating = review.getRating();
            this.book = (review.getBook() != null) ? new BookWithAllDTO(review.getBook()) : null;
            this.user = (review.getUser() != null) ? new UserWithBooksDTO(review.getUser()) : null;
        }
    }

    // Getters e Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

//    public String getReviewerName() {
//        return reviewerName;
//    }
//
//    public void setReviewerName(String reviewerName) {
//        this.reviewerName = reviewerName;
//    }

    public UserWithBooksDTO getUser() {
        return user;
    }

    public void setUser(UserWithBooksDTO user) {
        this.user = user;
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

    public BookWithAllDTO getBook() {
        return book;
    }

    public void setBook(BookWithAllDTO book) {
        this.book = book;
    }
}
