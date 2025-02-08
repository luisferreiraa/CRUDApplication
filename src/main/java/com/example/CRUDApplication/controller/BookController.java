package com.example.CRUDApplication.controller;

import com.example.CRUDApplication.dto.BookDTO;
import com.example.CRUDApplication.model.Author;
import com.example.CRUDApplication.model.Book;
import com.example.CRUDApplication.model.Publisher;
import com.example.CRUDApplication.repo.AuthorRepo;
import com.example.CRUDApplication.repo.BookRepo;
import com.example.CRUDApplication.repo.PublisherRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController                 // Define esta classe como um controlador REST
@RequestMapping("/api")         // Define o prefixo base para todas as rotas
public class BookController {

    @Autowired                  // Injeta automaticamente a dependência do repositório
    private BookRepo bookRepo;

    @Autowired
    private AuthorRepo authorRepo;

    @Autowired
    private PublisherRepo publisherRepo;

    @GetMapping("/getAllBooks") // Endpoint para buscar todos os livros
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        try {
            List<Book> bookList = bookRepo.findAll(); // Busca todos os livros na base de dados

            if (bookList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Retorna status 204 se a lista estiver vazia
            }

            // Converte a lista de Book para BookDTO
            List<BookDTO> bookDTOList = bookList.stream()
                    .map(BookDTO::new)
                    .collect(Collectors.toList());

            return new ResponseEntity<>(bookDTOList, HttpStatus.OK); // Retorna lista de DTOs com status 200

        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // Retorna erro 500 em caso de falha
        }
    }

    @GetMapping(value = "/getBookById/{id}", produces = MediaType.APPLICATION_JSON_VALUE)   // Define um endpoint para obter um livro pelo ID
    public ResponseEntity<BookDTO> getBookById(@PathVariable Long id) {     // Recebe o ID do livro como parâmetro
        Optional<Book> bookData = bookRepo.findById(id);        // Busca o livro na base de dados

        // Se o livro for encontrado, retorna um DTO com status 200 (OK), senão retorna 404 (Not Found)
        return bookData.map(book -> new ResponseEntity<>(new BookDTO(book), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

//    @PostMapping("/addBook")        // Define um enpoint para adicionar um livro
//    public ResponseEntity<?> addBook(@RequestBody Book book) {      // Recebe os dados do livro no corpo da requisição
//        try {
//            // Verifica se o livro possui um publisher válido
//            if (book.getPublisher() == null || book.getPublisher().getId() == null) {
//                return new ResponseEntity<>("Publisher ID is required", HttpStatus.BAD_REQUEST);
//            }
//
//            // Busca o publisher pelo ID na base de dados
//            Optional<Publisher> publisherData = publisherRepo.findById(book.getPublisher().getId());
//            if (!publisherData.isPresent()) {
//                return new ResponseEntity<>("Publisher not found", HttpStatus.NOT_FOUND);
//            }
//
//            // Define o publisher do livro e guarda na base de dados
//            book.setPublisher(publisherData.get());
//            Book savedBook = bookRepo.save(book);
//            return new ResponseEntity<>(savedBook, HttpStatus.CREATED);     // Retorna 201 (Created)
//
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);  // Retorna 500 em caso de erro
//        }
//    }

    @PostMapping("/addBook") // Define um endpoint para adicionar um livro
    public ResponseEntity<?> addBook(@RequestBody Book book) {
        try {
            // Verifica se o livro possui um publisher válido
            if (book.getPublisher() == null || book.getPublisher().getId() == null) {
                return new ResponseEntity<>("Publisher ID is required", HttpStatus.BAD_REQUEST);
            }

            // Busca o publisher pelo ID na base de dados
            Optional<Publisher> publisherData = publisherRepo.findById(book.getPublisher().getId());
            if (!publisherData.isPresent()) {
                return new ResponseEntity<>("Publisher not found", HttpStatus.NOT_FOUND);
            }

            // Define o publisher do livro
            book.setPublisher(publisherData.get());

            // Verifica e adiciona autores se existirem
            if (book.getAuthors() != null && !book.getAuthors().isEmpty()) {
                List<Author> validAuthors = new ArrayList<>();
                for (Author author : book.getAuthors()) {
                    Optional<Author> authorData = authorRepo.findById(author.getId());
                    authorData.ifPresent(validAuthors::add);
                }

                if (validAuthors.isEmpty()) {
                    return new ResponseEntity<>("No valid authors found", HttpStatus.BAD_REQUEST);
                }

                book.setAuthors(validAuthors); // Define os autores válidos
            }

            // Guarda o livro na base de dados
            Book savedBook = bookRepo.save(book);
            return new ResponseEntity<>(savedBook, HttpStatus.CREATED); // Retorna 201 (Created)

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // Retorna 500 em caso de erro
        }
    }


    @PutMapping("/updateBookById/{id}")     // Define um endpoint PUT para atualizar um livro pelo ID
    public ResponseEntity<?> updateBookById(@PathVariable Long id, @RequestBody Book newBookData) {
        Optional<Book> oldBookData = bookRepo.findById(id);     // Busca o livro na base de dados

        if (oldBookData.isPresent()) {      // Verifica se o livro foi encontrado
            Book updatedBook = oldBookData.get();       // Obtém o livro existente
            updatedBook.setTitle(newBookData.getTitle());       // Atualiza o título
            updatedBook.setAuthors(newBookData.getAuthors());     // Atualiza o autor

            // Verifica se um novo publisher foi enviado na requisição
            if (newBookData.getPublisher() != null && newBookData.getPublisher().getId() != null) {
                Optional<Publisher> publisherData = publisherRepo.findById(newBookData.getPublisher().getId());
                if (!publisherData.isPresent()) {
                    return new ResponseEntity<>("Publisher not found", HttpStatus.NOT_FOUND);
                }
                updatedBook.setPublisher(publisherData.get());      // Atualiza o publisher
            }

            Book savedBook = bookRepo.save(updatedBook);        // Guarda as alterações na base de dados
            return new ResponseEntity<>(savedBook, HttpStatus.OK);      // Retorna 200 (OK)
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);      // Retorna 400 se o livro não for encontrado
    }

    @DeleteMapping("/deleteBookById/{id}")      // Define um endpoint DELETE para remover um livro pelo ID
    public ResponseEntity<HttpStatus> deleteBookById(@PathVariable Long id) {
        try {
            bookRepo.deleteById(id);        // Remove o livro pelo ID
            return new ResponseEntity<>(HttpStatus.OK);     // Retorna 200 (OK) se a exclusão for bem-sucedida
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);      // Retorna 500 em caso de erro
        }
    }
}