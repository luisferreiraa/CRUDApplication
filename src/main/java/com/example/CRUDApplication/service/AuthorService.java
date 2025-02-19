package com.example.CRUDApplication.service;

import com.example.CRUDApplication.dto.AuthorWithBooksDTO;
import com.example.CRUDApplication.dto.AuthorCreateRequest;
import com.example.CRUDApplication.model.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorService {
    List<AuthorWithBooksDTO> getAllAuthors();

    Optional<AuthorWithBooksDTO> getAuthorById(Long id);

    Author addAuthor(AuthorCreateRequest author);

    Author updateAuthorName(Long id, AuthorCreateRequest updateData);

    boolean deleteAuthorById(Long id);
}
