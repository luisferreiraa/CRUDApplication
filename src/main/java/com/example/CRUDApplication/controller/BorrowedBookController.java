package com.example.CRUDApplication.controller;

import com.example.CRUDApplication.dto.BorrowedBookDTO;
import com.example.CRUDApplication.model.BorrowedBook;
import com.example.CRUDApplication.repo.BorrowedBookRepo;
import com.example.CRUDApplication.service.BorrowedBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/borrowed-books")
public class BorrowedBookController {

    @Autowired
    private BorrowedBookService borrowedBookService;

    @Autowired
    private BorrowedBookRepo borrowedBookRepo;

    // Criar um novo empréstimo de livro
    @PostMapping("/")
    public ResponseEntity<BorrowedBook> borrowBook(@RequestBody BorrowedBookDTO borrowedBookDTO) {
        BorrowedBook borrowedBook = borrowedBookService.borrowBook(borrowedBookDTO);
        return new ResponseEntity<>(borrowedBook, HttpStatus.CREATED);
    }

    // Listar todos os livros emprestados
    @GetMapping("/")
    public ResponseEntity<List<BorrowedBook>> getAllBorrowedBooks() {
        List<BorrowedBook> borrowedBooks = borrowedBookService.getAllBorrowedBooks();
        return ResponseEntity.ok(borrowedBooks);
    }

    // Obter todos os livros emprestados por um utilizador específico
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BorrowedBook>> getBorrowedBooksByUser(@PathVariable Long userId) {
        List<BorrowedBook> borrowedBook = borrowedBookService.getBorrowedBooksByUser(userId);
        return ResponseEntity.ok(borrowedBook);
    }

    // Remover um empréstimo (devolver o livro)
    @DeleteMapping("/{userId}/{bookId}")
    public ResponseEntity<Void> returnBook(@PathVariable Long userId, @PathVariable Long bookId) {
        borrowedBookService.returnBook(userId, bookId);
        return ResponseEntity.noContent().build();
    }
}
