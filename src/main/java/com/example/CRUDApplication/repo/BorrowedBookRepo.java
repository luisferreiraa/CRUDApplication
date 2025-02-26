package com.example.CRUDApplication.repo;

import com.example.CRUDApplication.model.BorrowedBook;
import com.example.CRUDApplication.model.BorrowedBookId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BorrowedBookRepo extends JpaRepository<BorrowedBook, BorrowedBookId> {
    List<BorrowedBook> findByUserId(Long userId);
}
