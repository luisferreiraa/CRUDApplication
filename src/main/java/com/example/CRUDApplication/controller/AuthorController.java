package com.example.CRUDApplication.controller;

import com.example.CRUDApplication.dto.AuthorDTO;
import com.example.CRUDApplication.model.Author;
import com.example.CRUDApplication.model.Book;
import com.example.CRUDApplication.model.Publisher;
import com.example.CRUDApplication.repo.AuthorRepo;
import com.example.CRUDApplication.repo.BookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {

    @Autowired
    private AuthorRepo authorRepo;

    @Autowired
    private BookRepo bookRepo;

    @GetMapping("/getAll")
    public ResponseEntity<List<AuthorDTO>> getAllAuthors() {
        try {
            List<Author> authorList = authorRepo.findAll();

            if (authorList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            List<AuthorDTO> authorDTOList = authorList.stream().map(AuthorDTO::new).toList();

            return new ResponseEntity<>(authorDTOList, HttpStatus.OK);

        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/getById/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthorDTO> getAuthorById(@PathVariable Long id) {
        Optional<Author> authorData = authorRepo.findById(id);

        return authorData.map(author -> new ResponseEntity<>(new AuthorDTO(author), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/add")        // Endpoint para adicionar um novo autor
    public ResponseEntity<?> addAuthor(@RequestBody Author author) {       // Objeto Author enviado no corpo da requisição
        try {
            if (author.getName() == null || author.getName().trim().isEmpty()) {
                return new ResponseEntity<>("Author name is required", HttpStatus.BAD_REQUEST);
            }
            Author savedAuthor = authorRepo.save(author);
            return new ResponseEntity<>(savedAuthor, HttpStatus.CREATED);        // Retorna Author guardado com 201 (CREATED)
        } catch (Exception e) {
            return new ResponseEntity<>("Error saving author", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping("/updateById/{id}")
    public ResponseEntity<?> updateAuthorName(@PathVariable Long id, @RequestBody Map<String, String> updateData) {
        Optional<Author> authorData = authorRepo.findById(id);

        if (authorData.isPresent()) {
            Author updatedAuthor = authorData.get();

            // Verifica se "name" foi enviado no JSON
            if (updateData.containsKey("name") && updateData.get("name") != null) {
                updatedAuthor.setName(updateData.get("name"));
                authorRepo.save(updatedAuthor);
                return new ResponseEntity<>(updatedAuthor, HttpStatus.OK);
            }
            return new ResponseEntity<>("Name is required", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Author not found", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<HttpStatus> deleteAuthorById(@PathVariable Long id) {
        try {
            authorRepo.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{authorId}/addBook/{bookId}")        // Endpoint para adicionar um livro a um autor
    public ResponseEntity<?> addBookToAuthor(@PathVariable Long authorId, @PathVariable Long bookId) {
        Optional<Author> authorData = authorRepo.findById(authorId);
        Optional<Book> bookData = bookRepo.findById(bookId);

        if (authorData.isPresent() && bookData.isPresent()) {
            Author author = authorData.get();
            Book book = bookData.get();

            // Evita adicionar duplicado
            if (!author.getBooks().contains(book)) {
                author.getBooks().add(book);
                authorRepo.save(author);
                return new ResponseEntity<>(author, HttpStatus.OK);
            }
            return new ResponseEntity<>("Book already associated with author", HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>("Author or Book not found", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{authorId}/removeBook/{bookId}")       // Endpoint para remover um livro de um autor
    public ResponseEntity<?> removeBookFromAuthor(@PathVariable Long authorId, @PathVariable Long bookId) {
        Optional<Author> authorData = authorRepo.findById(authorId);
        Optional<Book> bookData = bookRepo.findById(bookId);

        if (authorData.isPresent() && bookData.isPresent()) {
            Author author = authorData.get();
            Book book = bookData.get();

            if (author.getBooks().contains(book)) {
                author.getBooks().remove(book);
                authorRepo.save(author);
                return new ResponseEntity<>(author, HttpStatus.OK);
            }
            return new ResponseEntity<>("Book not associated with author", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Author or Book not found", HttpStatus.NOT_FOUND);
    }
}
