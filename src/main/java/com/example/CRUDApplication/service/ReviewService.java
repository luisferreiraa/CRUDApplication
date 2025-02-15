package com.example.CRUDApplication.service;

import com.example.CRUDApplication.model.Review;

import java.util.List;

public interface ReviewService {
    List<Review> getAllReviewsByBookId(Long bookId);

}
