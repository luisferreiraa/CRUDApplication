package com.example.CRUDApplication.controller;

import com.example.CRUDApplication.dto.AuthorRequest;
import com.example.CRUDApplication.dto.AuthorDTO;
import com.example.CRUDApplication.model.Author;
import com.example.CRUDApplication.repo.AuthorRepo;
import com.example.CRUDApplication.repo.BookRepo;
import com.example.CRUDApplication.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {

    @Autowired
    private AuthorRepo authorRepo;

    @Autowired
    private BookRepo bookRepo;

    @Autowired
    private AuthorService authorService;

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllAuthors() {
        try {
            List<Author> authorList = authorService.getAllAuthors();
            return ResponseEntity.ok(authorList);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving authors");
        }
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<?> getAuthorById(@PathVariable Long id) {
        try {
            AuthorDTO author = authorService.getAuthorById(id)
                    .orElseThrow(() -> new NoSuchElementException("No author found with this ID"));

            return ResponseEntity.ok(author);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving author");
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addAuthor(@RequestBody AuthorRequest author) {
        try {
            Author savedAuthor = authorService.addAuthor(author);
            return new ResponseEntity<>(savedAuthor, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating author");
        }
    }

    @PutMapping("/updateById/{id}")
    public ResponseEntity<?> updateAuthorName(@PathVariable Long id, @RequestBody AuthorRequest updateData) {
        try {
            Author updatedAuthor = authorService.updateAuthorName(id, updateData);
            return ResponseEntity.ok(updatedAuthor);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating author");
        }
    }

    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<?> deleteAuthorById(@PathVariable Long id) {
        try {
            boolean deletedAuthor = authorService.deleteAuthorById(id);

            if (deletedAuthor) {
                return ResponseEntity.status(HttpStatus.OK).body("Author deleted successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Author not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting author");
        }
    }
}
