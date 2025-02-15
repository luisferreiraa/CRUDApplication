package com.example.CRUDApplication.service;

import com.example.CRUDApplication.dto.BookDTO;
import com.example.CRUDApplication.dto.BookRequest;
import com.example.CRUDApplication.dto.ReviewDTO;
import com.example.CRUDApplication.model.Book;
import com.example.CRUDApplication.model.Review;

import java.util.List;
import java.util.Optional;

public interface BookService {
    List<Book> getAllBooks();
    Optional<BookDTO> getBookById(Long id);
    Book addBook(BookRequest book);
    Book updateBookTitle(Long id, BookRequest updateBook);
    Book updateBookCopies(Long bookId, Integer copies);
    boolean deleteBookById(Long id);
    Book addAuthorToBook(Long bookId, Long authorId);
    Book removeAuthorFromBook(Long bookId, Long authorId);
    Book addCategoryToBook(Long bookId, Long categoryId);
    Book removeCategoryFromBook(Long bookId, Long categoryId);
    Book addPublisherToBook(Long bookId, Long publisherId);
    Book addReviewToBook(Long bookId, Long userId, ReviewDTO review);
    Book removeReviewFromBook(Long bookId, Long reviewId);
}
