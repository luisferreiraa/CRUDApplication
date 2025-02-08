package com.example.CRUDApplication.controller;

import com.example.CRUDApplication.model.Book;
import com.example.CRUDApplication.model.Review;
import com.example.CRUDApplication.repo.BookRepo;
import com.example.CRUDApplication.repo.ReviewRepo;
import com.example.CRUDApplication.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    @GetMapping("/getAllByBookId/{bookId}")
    public ResponseEntity<List<Review>> getAllReviewsByBookId (@PathVariable Long bookId) {
        try {
            // Verifica se o livro existe
            Optional<Book> bookData = bookRepo.findById(bookId);
            if (bookData.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            // Busca todas as reviews associadas ao livro
            List<Review> reviews = reviewRepo.findByBookId(bookId);

            if (reviews.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(reviews, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addReview(@RequestBody Review review) {
        try {
            // Verifica se o livro associado à review é válido
            if (review.getBook() == null || review.getBook().getId() == null) {
                return new ResponseEntity<>("Book ID is required", HttpStatus.BAD_REQUEST);
            }

            // Busca livro pelo ID
            Optional<Book> bookData = bookRepo.findById(review.getBook().getId());
            if (bookData.isEmpty()) {
                return new ResponseEntity<>("Book not found", HttpStatus.NOT_FOUND);
            }

            // Define o livro da review
            review.setBook(bookData.get());

            // Guarda a review na base de dados
            Review savedReview = reviewRepo.save(review);

            return new ResponseEntity<>(savedReview, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }





}
