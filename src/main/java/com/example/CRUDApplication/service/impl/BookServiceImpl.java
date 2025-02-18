package com.example.CRUDApplication.service.impl;

import com.example.CRUDApplication.dto.BookDTO;
import com.example.CRUDApplication.dto.BookRequest;
import com.example.CRUDApplication.dto.ReviewDTO;
import com.example.CRUDApplication.exception.ObjectNotFoundException;
import com.example.CRUDApplication.exception.RequestDataMissingException;
import com.example.CRUDApplication.model.*;
import com.example.CRUDApplication.repo.*;
import com.example.CRUDApplication.service.BookService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// Contém a lógica de negócio
// Faz validações relacionadas às regras do domínio
// Interage com repositórios para acessar a base de dados
// Não deve lidar com detalhes de HTTP (como códigos de status)

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepo bookRepo;

    @Autowired
    private AuthorRepo authorRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private PublisherRepo publisherRepo;

    @Autowired
    private ReviewRepo reviewRepo;

    @Autowired
    private UserRepo userRepo;

    @Override
    public List<Book> getAllBooks() {
        // Busca todos os livros da base de dados
        List<Book> bookList = bookRepo.findAll();

        // Se não encontrar books, lança excepção
        if (bookList.isEmpty()) {
            throw new ObjectNotFoundException("No books available in the system");
        }
        // Se encontrar, devolve a lista de livros
        return bookList;
    }

    @Override
    public Optional<BookDTO> getBookById(Long id) {
        // Busca um book pelo ID
        Optional<Book> bookDB = bookRepo.findById(id);

        // Se o book não existir, lança uma excepção
        if (bookDB.isEmpty()) {
            throw new ObjectNotFoundException("No book found with ID: " + id);
        }

        // Converte o Book em BookDTO e retorna
        return bookDB.map(BookDTO::new);
    }

    @Override
    public Book addBook(BookRequest newBook) {
        // Valida se o título do livro foi fornecido
        if (newBook.getTitle() == null || newBook.getTitle().trim().isEmpty()) {
            throw new RequestDataMissingException("Book title is required");
        }

        // Cria uma nova entidade a partir do DTO recebido
        Book savedBook = new Book();
        savedBook.setTitle(newBook.getTitle());

        // Salva o book na base de dados e retorna a entidade persistida
        return bookRepo.save(savedBook);
    }

    @Override
    public Book updateBookTitle(Long id, BookRequest updateData) {
        // Valida se o título do book foi fornecido
        if (updateData.getTitle() == null || updateData.getTitle().trim().isEmpty()) {
            throw new RequestDataMissingException("Book title is required");
        }

        // Busca o book pelo ID e lança uma excepção se não for encontrado
        Book bookDB = bookRepo.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Book not found with ID: " + id));

        // Atualiza o título do book
        bookDB.setTitle(updateData.getTitle());

        // Salva e retorna o book atualizado
        return bookRepo.save(bookDB);
    }

    @Override
    public Book updateBookCopies(Long bookId, Integer copies) {
        // Valida se o número de cópias foi fornecido
        if (copies == null) {
            throw new RequestDataMissingException("Number of copies is required");
        }

        // Busca o book pelo ID e lança uma excepção se não for encontrado
        Book bookDB = bookRepo.findById(bookId)
                .orElseThrow(() -> new ObjectNotFoundException("Book not found with ID: " + bookId));

        // Atuaçiza o número de exemplares
        bookDB.setCopies(copies);

        return bookRepo.save(bookDB);
    }

    @Override
    public boolean deleteBookById(Long id) {
        // Verifica se o book existe antes de remover
        if (bookRepo.existsById(id)) {
            bookRepo.deleteById(id);
            return true;
        }
        throw new ObjectNotFoundException("Book not found with ID: " + id);
    }

    @Override
    @Transactional
    public Book addAuthorToBook(Long bookId, Long authorId) {
        Book book = bookRepo.findById(bookId)
                .orElseThrow(() -> new ObjectNotFoundException("Book not found with ID: " + bookId));

        Author author = authorRepo.findById(authorId)
                .orElseThrow(() -> new ObjectNotFoundException("Author not found with ID: " + authorId));

        book.getAuthors().add(author);

        return bookRepo.save(book);
    }

    @Override
    @Transactional
    public Book removeAuthorFromBook(Long bookId, Long authorId) {
        Book book = bookRepo.findById(bookId)
                .orElseThrow(() -> new ObjectNotFoundException("Book not found with ID: " + bookId));

        Author author = authorRepo.findById(authorId)
                .orElseThrow(() -> new RuntimeException("Author not found with ID: " + authorId));

        book.getAuthors().remove(author);

        return bookRepo.save(book);
    }

    @Override
    @Transactional
    public Book addCategoryToBook(Long bookId, Long categoryId) {
        Book book = bookRepo.findById(bookId)
                .orElseThrow(() -> new ObjectNotFoundException("Book not found with ID: " + bookId));

        Category category = categoryRepo.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found with ID: " + categoryId));

        book.getCategories().add(category);

        return bookRepo.save(book);
    }

    @Override
    @Transactional
    public Book removeCategoryFromBook(Long bookId, Long categoryId) {
        Book book = bookRepo.findById(bookId)
                .orElseThrow(() -> new ObjectNotFoundException("Book not found with ID: " + bookId));

        Category category = categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ObjectNotFoundException("Category not found with ID: " + categoryId));

        book.getCategories().remove(category);

        return bookRepo.save(book);
    }

    @Override
    @Transactional
    public Book addPublisherToBook(Long bookId, Long publisherId) {
        Book book = bookRepo.findById(bookId)
                .orElseThrow(() -> new ObjectNotFoundException("Book not found with ID: " + bookId));

        Publisher publisher = publisherRepo.findById(publisherId)
                .orElseThrow(() -> new ObjectNotFoundException("Publisher not found with ID: " + publisherId));

        book.setPublisher(publisher);

        return bookRepo.save(book);
    }

    @Override
    public Book addReviewToBook(Long bookId, Long userId, ReviewDTO reviewDTO) {
        Book book = bookRepo.findById(bookId)
                .orElseThrow(() -> new ObjectNotFoundException("Book not found with ID: " + bookId));

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ObjectNotFoundException("User not found with ID: " + userId));

        // Verificar se a lista de reviews está nula e inicializá-la se necessário
        if (book.getReviews() == null) {
            book.setReviews(new ArrayList<>());
        }

        // Cria a review a partir do DTO
        Review review = new Review();
        review.setComment(reviewDTO.getComment());
        review.setRating(reviewDTO.getRating());
//        review.setReviewerName(reviewDTO.getReviewerName());
        review.setUser(user);
        review.setBook(book);

        // Adciona a review ao livro
        book.getReviews().add(review);
        // Salva a review na base de dados
        reviewRepo.save(review);

        // Salva o livro com a nova review
        return bookRepo.save(book);
    }

    @Override
    public Book removeReviewFromBook(Long bookId, Long reviewId) {
        Book book = bookRepo.findById(bookId)
                .orElseThrow(() -> new ObjectNotFoundException("Book not found with ID: " + bookId));

        Review review = reviewRepo.findById(reviewId)
                .orElseThrow(() -> new ObjectNotFoundException("Review not found with ID: " + reviewId));

        if (!book.getReviews().contains(review)) {
            throw new ObjectNotFoundException("Review does not belong to this book");
        }

        book.getReviews().remove(review);
        reviewRepo.delete(review);

        return bookRepo.save(book);
    }







}
