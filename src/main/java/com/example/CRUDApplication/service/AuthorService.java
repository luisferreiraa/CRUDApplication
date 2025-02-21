package com.example.CRUDApplication.service;

import com.example.CRUDApplication.dto.AuthorWithBooksDTO;
import com.example.CRUDApplication.dto.AuthorNameDTO;
import com.example.CRUDApplication.model.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorService {
    List<AuthorWithBooksDTO> getAllAuthors();

    Optional<AuthorWithBooksDTO> getAuthorById(Long id);

    Author addAuthor(AuthorNameDTO author);

    Author updateAuthorName(Long id, AuthorNameDTO updateData);

    boolean deleteAuthorById(Long id);
}
