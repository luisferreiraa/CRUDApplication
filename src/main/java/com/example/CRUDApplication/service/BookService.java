package com.example.CRUDApplication.service;

import com.example.CRUDApplication.dto.BookDTO;
import com.example.CRUDApplication.dto.BookWithAllDTO;
import com.example.CRUDApplication.dto.BookTitleDTO;
import com.example.CRUDApplication.dto.ReviewDTO;
import com.example.CRUDApplication.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {
    List<BookDTO> getAllBooks();
    Optional<BookWithAllDTO> getBookById(Long id);
    BookDTO addBook(BookTitleDTO book);
    BookDTO updateBookTitle(Long id, BookTitleDTO updateBook);
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
