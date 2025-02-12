package com.example.CRUDApplication.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "publishers")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Publisher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)     // ID incremento de 1 em 1
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    // mappedBy = "publisher" -> Um Publisher pode ter vários Books
    // cascade = CascadeType.All -> Quando um publisher for apagado, os seus livros também serão
    // orphanRemoval = True -> Se um livro deixar de ter Publisher, será removido da BD
    // fetch = FetchType.LAZY -> O JPA não carrega automaticamente as coleções @OneToMany
    @OneToMany(mappedBy = "publisher", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference       // Evita recursão infinita
    private List<Book> books;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
