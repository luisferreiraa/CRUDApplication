package com.example.CRUDApplication.controller;

import com.example.CRUDApplication.dto.ReviewDTO;
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

    @GetMapping("/{bookId}")
    public ResponseEntity<List<ReviewDTO>> getAllReviewsByBookId(@PathVariable Long bookId) {
        List<ReviewDTO> reviewList = reviewService.getAllReviewsByBookId(bookId);
        return ResponseEntity.ok(reviewList);
    }

    // todo Criar /getAllByUserId


}
