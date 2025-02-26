package com.example.CRUDApplication.service;

import com.example.CRUDApplication.dto.BorrowedBookDTO;
import com.example.CRUDApplication.model.BorrowedBook;

import java.util.List;

public interface BorrowedBookService {
    BorrowedBook borrowBook(BorrowedBookDTO borrowedBookDTO);
    List<BorrowedBook> getAllBorrowedBooks();
    List<BorrowedBook> getBorrowedBooksByUser(Long userId);
    void returnBook(Long userId, Long bookId);
}
