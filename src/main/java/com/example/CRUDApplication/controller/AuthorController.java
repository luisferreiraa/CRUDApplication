package com.example.CRUDApplication.controller;

import com.example.CRUDApplication.dto.AuthorRequest;
import com.example.CRUDApplication.dto.AuthorDTO;
import com.example.CRUDApplication.dto.CategoryRequest;
import com.example.CRUDApplication.model.Author;
import com.example.CRUDApplication.model.Category;
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

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllAuthors() {
        try {
            List<Author> authorList = authorService.getAllAuthors();
            return ResponseEntity.ok(authorList);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No authors found");
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
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No author found with this ID");
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
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Author name is required");
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
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Author not found");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid name");
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

    @PostMapping("/{authorId}/addBook/{bookId}")
    public ResponseEntity<?> addBookToAuthor(@PathVariable Long authorId, @PathVariable Long bookId) {
        try {
            Author updatedAuthor = authorService.addBookToAuthor(authorId, bookId);
            return ResponseEntity.status(HttpStatus.OK).body("Book added to author");
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding book to author");
        }
    }

    @DeleteMapping("/{authorId}/removeBook/{bookId}")
    public ResponseEntity<?> removeBookFromAuthor(@PathVariable Long authorId, @PathVariable Long bookId) {
        try {
            Optional<Author> updatedAuthor = authorService.removeBookFromAuthor(authorId, bookId);

            if (updatedAuthor.isPresent()) {
                return ResponseEntity.ok(updatedAuthor.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Author or Book not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error removing book from author");
        }
    }


    // Controllers sem utilizar Service

//    @GetMapping("/getAll")
//    public ResponseEntity<List<AuthorDTO>> getAllAuthors() {
//        try {
//            List<Author> authorList = authorRepo.findAll();
//
//            if (authorList.isEmpty()) {
//                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//            }
//            List<AuthorDTO> authorDTOList = authorList.stream().map(AuthorDTO::new).toList();
//
//            return new ResponseEntity<>(authorDTOList, HttpStatus.OK);
//
//        } catch (Exception ex) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

//    @GetMapping(value = "/getById/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<AuthorDTO> getAuthorById(@PathVariable Long id) {
//        Optional<Author> authorData = authorRepo.findById(id);
//
//        return authorData.map(author -> new ResponseEntity<>(new AuthorDTO(author), HttpStatus.OK))
//                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
//    }

//    @PostMapping("/add")        // Endpoint para adicionar um novo autor
//    public ResponseEntity<?> addAuthor(@RequestBody AuthorRequest authorRequest) {       // Objeto Author enviado no corpo da requisição
//        try {
//            if (authorRequest.getName() == null || authorRequest.getName().trim().isEmpty()) {
//                return new ResponseEntity<>("Author name is required", HttpStatus.BAD_REQUEST);
//            }
//
//            // Converter DTO para entidade Author
//            Author author = new Author();
//            author.setName(authorRequest.getName());
//
//            // Salvar na base de dados
//            Author savedAuthor = authorRepo.save(author);
//
//            return new ResponseEntity<>(savedAuthor, HttpStatus.CREATED);        // Retorna Author guardado com 201 (CREATED)
//        } catch (Exception e) {
//            return new ResponseEntity<>("Error saving author", HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//
//    }

//    @PutMapping("/updateById/{id}")
//    public ResponseEntity<AuthorDTO> updateAuthorName(@PathVariable Long id, @RequestBody AuthorDTO updateData) {
//        if (updateData.getName() == null || updateData.getName().trim().isEmpty()) {
//            return ResponseEntity.badRequest().build();  // Retorna 400 sem mensagem em String
//        }
//
//        return authorService.updateAuthorName(id, updateData)
//                .map(ResponseEntity::ok)  // Usa ResponseEntity.ok() diretamente
//                .orElseGet(() -> ResponseEntity.notFound().build());  // Retorna 404 corretamente
//    }

//    @DeleteMapping("/deleteById/{id}")
//    public ResponseEntity<HttpStatus> deleteAuthorById(@PathVariable Long id) {
//        try {
//            authorRepo.deleteById(id);
//            return new ResponseEntity<>(HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

//    @PostMapping("/{authorId}/addBook/{bookId}")        // Endpoint para adicionar um livro a um autor
//    public ResponseEntity<?> addBookToAuthor(@PathVariable Long authorId, @PathVariable Long bookId) {
//        Optional<Author> authorData = authorRepo.findById(authorId);
//        Optional<Book> bookData = bookRepo.findById(bookId);
//
//        if (authorData.isPresent() && bookData.isPresent()) {
//            Author author = authorData.get();
//            Book book = bookData.get();
//
//            // Evita adicionar duplicado
//            if (!author.getBooks().contains(book)) {
//                author.getBooks().add(book);
//                authorRepo.save(author);
//                return new ResponseEntity<>(author, HttpStatus.OK);
//            }
//            return new ResponseEntity<>("Book already associated with author", HttpStatus.CONFLICT);
//        }
//        return new ResponseEntity<>("Author or Book not found", HttpStatus.NOT_FOUND);
//    }

//    @DeleteMapping("/{authorId}/removeBook/{bookId}")       // Endpoint para remover um livro de um autor
//    public ResponseEntity<?> removeBookFromAuthor(@PathVariable Long authorId, @PathVariable Long bookId) {
//        Optional<Author> authorData = authorRepo.findById(authorId);
//        Optional<Book> bookData = bookRepo.findById(bookId);
//
//        if (authorData.isPresent() && bookData.isPresent()) {
//            Author author = authorData.get();
//            Book book = bookData.get();
//
//            if (author.getBooks().contains(book)) {
//                author.getBooks().remove(book);
//                authorRepo.save(author);
//                return new ResponseEntity<>(author, HttpStatus.OK);
//            }
//            return new ResponseEntity<>("Book not associated with author", HttpStatus.NOT_FOUND);
//        }
//        return new ResponseEntity<>("Author or Book not found", HttpStatus.NOT_FOUND);
//    }


}
