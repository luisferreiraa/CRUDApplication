package com.example.CRUDApplication.service;

import com.example.CRUDApplication.dto.AuthorDTO;
import com.example.CRUDApplication.dto.AuthorRequest;
import com.example.CRUDApplication.model.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorService {
    List<Author> getAllAuthors();

    Optional<AuthorDTO> getAuthorById(Long id);

    Author addAuthor(AuthorRequest author);

    Author updateAuthorName(Long id, AuthorRequest updateData);

    boolean deleteAuthorById(Long id);

    Author addBookToAuthor(Long authorId, Long bookId);

    boolean removeBookFromAuthor(Long authorId, Long bookId);

}
