package com.example.CRUDApplication.service.impl;

import com.example.CRUDApplication.dto.BorrowedBookDTO;
import com.example.CRUDApplication.exception.ObjectNotFoundException;
import com.example.CRUDApplication.model.Book;
import com.example.CRUDApplication.model.BorrowedBook;
import com.example.CRUDApplication.model.BorrowedBookId;
import com.example.CRUDApplication.model.User;
import com.example.CRUDApplication.repo.BookRepo;
import com.example.CRUDApplication.repo.BorrowedBookRepo;
import com.example.CRUDApplication.repo.UserRepo;
import com.example.CRUDApplication.service.BorrowedBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BorrowedBookServiceImpl implements BorrowedBookService {

    @Autowired
    private BorrowedBookRepo borrowedBookRepo;

    @Autowired
    private BookRepo bookRepo;

    @Autowired
    private UserRepo userRepo;

    @Override
    public BorrowedBook borrowBook(BorrowedBookDTO borrowedBookDTO) {
        User user = userRepo.findById(borrowedBookDTO.getUserId())
                .orElseThrow(() -> new ObjectNotFoundException("User not found with ID: " + borrowedBookDTO.getUserId()));
        Book book = bookRepo.findById(borrowedBookDTO.getBookId())
                .orElseThrow(() -> new ObjectNotFoundException("Book not found with ID: " + borrowedBookDTO.getBookId()));

        BorrowedBook borrowedBook = new BorrowedBook(user, book, LocalDate.now());
        borrowedBookRepo.save(borrowedBook);

        return borrowedBook;
    }

    @Override
    public List<BorrowedBook> getAllBorrowedBooks() {
        return borrowedBookRepo.findAll();
    }

    public List<BorrowedBook> getBorrowedBooksByUser(Long userId) {
        return borrowedBookRepo.findByUserId(userId);
    }

    public void returnBook(Long userId, Long bookId) {
        BorrowedBookId id = new BorrowedBookId(userId, bookId);
        borrowedBookRepo.deleteById(id);
    }
}
