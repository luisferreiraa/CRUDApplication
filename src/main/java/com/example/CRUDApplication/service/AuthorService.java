package com.example.CRUDApplication.service;

import com.example.CRUDApplication.dto.AuthorDTO;
import com.example.CRUDApplication.dto.AuthorWithBooksDTO;
import com.example.CRUDApplication.dto.AuthorNameDTO;
import com.example.CRUDApplication.model.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface AuthorService {
    Page<AuthorDTO> getAllAuthors(Pageable pageable);
    Optional<AuthorWithBooksDTO> getAuthorById(Long id);
    AuthorDTO addAuthor(AuthorNameDTO author);
    AuthorDTO updateAuthorName(Long id, AuthorNameDTO updateData);
    boolean deleteAuthorById(Long id);
}
