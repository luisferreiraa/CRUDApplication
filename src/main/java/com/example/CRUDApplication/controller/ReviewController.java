package com.example.CRUDApplication.controller;

import com.example.CRUDApplication.model.Book;
import com.example.CRUDApplication.model.Review;
import com.example.CRUDApplication.repo.BookRepo;
import com.example.CRUDApplication.repo.ReviewRepo;
import com.example.CRUDApplication.repo.UserRepo;
import com.example.CRUDApplication.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired
    private ReviewRepo reviewRepo;

    @Autowired
    private BookRepo bookRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ReviewService reviewService;

    @GetMapping("/getAllByBookId/{bookId}")
    public ResponseEntity<?> getAllReviewsByBookId (@PathVariable Long bookId) {
        try {
            List<Review> reviewList = reviewService.getAllReviewsByBookId(bookId);
            return ResponseEntity.ok(reviewList);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    // todo Criar /getAllByUserId





}
