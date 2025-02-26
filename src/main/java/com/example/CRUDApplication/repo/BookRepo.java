package com.example.CRUDApplication.repo;

import com.example.CRUDApplication.dto.BookWithAllDTO;
import com.example.CRUDApplication.model.Book;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository         // Indica que esta interface é um componente Spring que gerencia a persistência de dados
public interface BookRepo extends JpaRepository<Book, Long> {
    // JpaRepository já fornece métodos CRUD básicos,
    // então não precisamos declarar nada manualmente a menos que desejemos consultas personalizadas.
    List<Book> findByAuthorsId(Long id);
    List<Book> findByCategoriesId(Long id);
    List<Book> findByPublishersId(Long id);
}
