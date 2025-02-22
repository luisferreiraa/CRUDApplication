package com.example.CRUDApplication.controller;

import com.example.CRUDApplication.dto.BookDTO;
import com.example.CRUDApplication.dto.BookWithAllDTO;
import com.example.CRUDApplication.dto.BookTitleDTO;
import com.example.CRUDApplication.dto.ReviewDTO;
import com.example.CRUDApplication.model.Book;

import com.example.CRUDApplication.repo.BookRepo;
import com.example.CRUDApplication.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController                 // Define esta classe como um controlador REST
@RequestMapping("/api/books")         // Define o prefixo base para todas as rotas
public class BookController {

    @Autowired                  // Injeta automaticamente a dependência do repositório
    private BookRepo bookRepo;

    @Autowired
    private BookService bookService;

    @GetMapping("/")
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        List<BookDTO> bookList = bookService.getAllBooks();
        return ResponseEntity.ok(bookList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<BookWithAllDTO>> getBookById(@PathVariable Long id) {
        Optional<BookWithAllDTO> book = bookService.getBookById(id);
        return ResponseEntity.ok(book);
    }

    @PostMapping("/")
    public ResponseEntity<BookDTO> addBook(@Valid @RequestBody BookTitleDTO book) {
        BookDTO savedBook = bookService.addBook(book);
        return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDTO> updateBookById(@PathVariable Long id, @Valid @RequestBody BookTitleDTO updateData) {
        BookDTO updatedBook = bookService.updateBookTitle(id, updateData);
        return new ResponseEntity<>(updatedBook, HttpStatus.OK);
    }

    @PutMapping("/{bookId}/{copies}")
    public ResponseEntity<Book> updateCopiesById(@PathVariable Long bookId, @PathVariable Integer copies) {
        Book updatedBook = bookService.updateBookCopies(bookId, copies);
        return new ResponseEntity<>(updatedBook, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBookById(@PathVariable Long id) {
        boolean deletedBook = bookService.deleteBookById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{bookId}/authors/{authorId}")
    public ResponseEntity<Book> addAuthorToBook(@PathVariable Long bookId, @PathVariable Long authorId) {
        Book updatedBook = bookService.addAuthorToBook(bookId, authorId);
        return ResponseEntity.ok(updatedBook);
    }

    @DeleteMapping("/{bookId}/authors/{authorId}")
    public ResponseEntity<Book> removeAuthorFromBook(@PathVariable Long bookId, @PathVariable Long authorId) {
        Book updatedBook = bookService.removeAuthorFromBook(bookId, authorId);
        return ResponseEntity.ok(updatedBook);
    }

    @PutMapping("/{bookId}/categories/{categoryId}")
    public ResponseEntity<Book> addCategoryToBook(@PathVariable Long bookId, @PathVariable Long categoryId) {
        Book updatedBook = bookService.addCategoryToBook(bookId, categoryId);
        return ResponseEntity.ok(updatedBook);
    }

    @DeleteMapping("/{bookId}/categories/{categoryId}")
    public ResponseEntity<Book> removeCategoryFromBook(@PathVariable Long bookId, @PathVariable Long categoryId) {
        Book updatedBook = bookService.removeCategoryFromBook(bookId, categoryId);
        return ResponseEntity.ok(updatedBook);
    }

    @PutMapping("/{bookId}/publishers/{publisherId}")
    public ResponseEntity<Book> addPublisherToBook(@PathVariable Long bookId, @PathVariable Long publisherId) {
        Book updatedBook = bookService.addPublisherToBook(bookId, publisherId);
        return ResponseEntity.ok(updatedBook);
    }

    @PutMapping("/{bookId}/reviews/{userId}")
    public ResponseEntity<Book> addReviewToBook(@PathVariable Long bookId, @PathVariable Long userId, @RequestBody ReviewDTO review) {
        Book updatedBook = bookService.addReviewToBook(bookId, userId, review);
        return ResponseEntity.ok(updatedBook);
    }

    @DeleteMapping("/{bookId}/reviews/{reviewId}")
    public ResponseEntity<Book> removeReviewFromBook(@PathVariable Long bookId, @PathVariable Long reviewId) {
        Book updatedBook = bookService.removeReviewFromBook(bookId, reviewId);
        return ResponseEntity.ok(updatedBook);
    }

//    @ExceptionHandler(BookNotFoundException.class)
//    public ResponseEntity<?> handleBookNotFoundException(BookNotFoundException exception) {
//        ErrorResponse booksNotFound = new ErrorResponse(
//                LocalDateTime.now(), exception.getMessage(), "Book List is empty");
//        return new ResponseEntity<>(booksNotFound, HttpStatus.NOT_FOUND);
//    }
}