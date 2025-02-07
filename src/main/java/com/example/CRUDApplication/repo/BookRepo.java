package com.example.CRUDApplication.repo;

import com.example.CRUDApplication.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository         // Indica que esta interface é um componente Spring que gerencia a persistência de dados
public interface BookRepo extends JpaRepository<Book, Long> {
    // JpaRepository já fornece métodos CRUD básicos,
    // então não precisamos declarar nada manualmente a menos que desejemos consultas personalizadas.
}
