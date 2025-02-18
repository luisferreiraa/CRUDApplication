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
import java.util.Optional;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {

    @Autowired
    private AuthorRepo authorRepo;

    @Autowired
    private BookRepo bookRepo;

    @Autowired
    private AuthorService authorService;

    @GetMapping("/")
    public ResponseEntity<List<Author>> getAllAuthors() {
        List<Author> authorList = authorService.getAllAuthors();
        return ResponseEntity.ok(authorList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<AuthorDTO>> getAuthorById(@PathVariable Long id) {
        Optional<AuthorDTO> author = authorService.getAuthorById(id);
        return ResponseEntity.ok(author);
    }

    @PostMapping("/")
    public ResponseEntity<Author> addAuthor(@RequestBody AuthorRequest author) {
        Author savedAuthor = authorService.addAuthor(author);
        return new ResponseEntity<>(savedAuthor, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Author> updateAuthorName(@PathVariable Long id, @RequestBody AuthorRequest updateData) {
        Author updatedAuthor = authorService.updateAuthorName(id, updateData);
        return ResponseEntity.ok(updatedAuthor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAuthorById(@PathVariable Long id) {
        boolean deletedAuthor = authorService.deleteAuthorById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
