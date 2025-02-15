package com.example.CRUDApplication.controller;

import com.example.CRUDApplication.dto.BookDTO;
import com.example.CRUDApplication.dto.BookRequest;
import com.example.CRUDApplication.dto.ReviewDTO;
import com.example.CRUDApplication.model.Book;
import com.example.CRUDApplication.model.Review;
import com.example.CRUDApplication.repo.BookRepo;
import com.example.CRUDApplication.service.BookService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController                 // Define esta classe como um controlador REST
@RequestMapping("/api/books")         // Define o prefixo base para todas as rotas
public class BookController {

    @Autowired                  // Injeta automaticamente a dependência do repositório
    private BookRepo bookRepo;

    @Autowired
    private BookService bookService;

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllBooks() {
        try {
            List<Book> bookList = bookService.getAllBooks();
            return ResponseEntity.ok(bookList);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving books");
        }
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<?> getBookById(@PathVariable Long id) {
        try {
            BookDTO book = bookService.getBookById(id)
                    .orElseThrow(() -> new NoSuchElementException("No book found with this ID"));

            return ResponseEntity.ok(book);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving book");
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addBook(@RequestBody BookRequest book) {
        try {
            Book savedBook = bookService.addBook(book);
            return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating book");
        }
    }


    @PutMapping("/updateById/{id}")
    public ResponseEntity<?> updateBookById(@PathVariable Long id, @RequestBody BookRequest updateData) {
      try {
          Book updatedBook = bookService.updateBookTitle(id, updateData);
          return new ResponseEntity<>(updatedBook, HttpStatus.OK);
      } catch (NoSuchElementException e) {
          return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
      } catch (IllegalArgumentException e) {
          return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
      } catch (Exception e) {
          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating book");
      }
    }

    @PutMapping("/updateCopiesById/{bookId}/{copies}")
    public ResponseEntity<?> updateCopiesById(@PathVariable Long bookId, @PathVariable Integer copies) {
        try {
            Book updatedBook = bookService.updateBookCopies(bookId, copies);
            return new ResponseEntity<>(updatedBook, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/deleteById/{id}")      // Define um endpoint DELETE para remover um livro pelo ID
    public ResponseEntity<?> deleteBookById(@PathVariable Long id) {
        try {
            boolean deletedBook = bookService.deleteBookById(id);

            if (deletedBook) {
                return ResponseEntity.status(HttpStatus.OK).body("Book deleted successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting book");
        }
    }

    @PutMapping("/{bookId}/addAuthor/{authorId}")
    public ResponseEntity<?> addAuthorToBook(@PathVariable Long bookId, @PathVariable Long authorId) {
        try {
            Book updatedBook = bookService.addAuthorToBook(bookId, authorId);
            return ResponseEntity.ok(updatedBook);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding author to book");
        }
    }

    @PutMapping("/{bookId}/removeAuthor/{authorId}")
    public ResponseEntity<?> removeAuthorFromBook(@PathVariable Long bookId, @PathVariable Long authorId) {
        try {
            Book updatedBook = bookService.removeAuthorFromBook(bookId, authorId);
            return ResponseEntity.ok(updatedBook);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error removing author from book");
        }
    }

    @PutMapping("/{bookId}/addCategory/{categoryId}")
    public ResponseEntity<?> addCategoryToBook(@PathVariable Long bookId, @PathVariable Long categoryId) {
        try {
            Book updatedBook = bookService.addCategoryToBook(bookId, categoryId);
            return ResponseEntity.ok(updatedBook);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding category to book");
        }
    }

    @PutMapping("/{bookId}/removeCategory/{categoryId}")
    public ResponseEntity<?> removeCategoryFromBook(@PathVariable Long bookId, @PathVariable Long categoryId) {
        try {
            Book updatedBook = bookService.removeCategoryFromBook(bookId, categoryId);
            return ResponseEntity.ok(updatedBook);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error removing category from book");
        }
    }

    @PutMapping("/{bookId}/addPublisher/{publisherId}")
    public ResponseEntity<?> addPublisherToBook(@PathVariable Long bookId, @PathVariable Long publisherId) {
        try {
            Book updatedBook = bookService.addPublisherToBook(bookId, publisherId);
            return ResponseEntity.ok(updatedBook);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding publisher to book");
        }
    }

    @PostMapping("/{bookId}/addReview/{userId}")
    public ResponseEntity<?> addReviewToBook(@PathVariable Long bookId, @PathVariable Long userId, @RequestBody ReviewDTO review) {
        try {
            Book updatedBook = bookService.addReviewToBook(bookId, userId, review);
            return ResponseEntity.ok(updatedBook);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding review to book");
        }
    }

    @DeleteMapping("/{bookId}/removeReview/{reviewId}")
    public ResponseEntity<?> removeReviewFromBook(@PathVariable Long bookId, @PathVariable Long reviewId) {
        try {
            Book updatedBook = bookService.removeReviewFromBook(bookId, reviewId);
            return ResponseEntity.ok(updatedBook);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error removing review from book");
        }
    }
}