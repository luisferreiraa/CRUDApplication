package com.example.CRUDApplication.model;

// Importa as anotações do Jakarta Persistance API (JPA) e Lombok
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity                     // Define esta classe como uma entidade JPA
@Table(name = "Books")      // Especifica o nome da tabela na base de dados
@NoArgsConstructor          // Gera automaticamente um construtor sem argumentos (necessário para JPA)
@AllArgsConstructor         // Gera automaticamente um construtor com todos os campos
@Setter                     // Gera automaticamente os setters para todos os campos
@Getter                     // Gera automaticamente os getters para todos os campos
@ToString                   // Gera automaticamente um método toString() para representação da classe
public class Book {

    @Id     // Define o campo 'id' como chave primária
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)                 // Define a coluna 'title' na base de dados
    private String title;

    @ManyToOne(fetch = FetchType.EAGER)      // Um Book pertence a um único Publisher
    @JoinColumn(name = "publisher_id")        // Define a chave estrangeira na tabela books
    @JsonManagedReference
    private Publisher publisher;

    @ManyToMany
    @JoinTable(
            name = "book_category",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    @JsonManagedReference
    private List<Category> categories;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference       // Evita o loop infinito na serialização
    private List<Review> reviews;

    @ManyToMany
    @JoinTable(
            name = "book_author",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    @JsonManagedReference
    private List<Author> authors;

    @Column
    private Integer copies;     // Total de cópias disponíveis no sistema

//    @ManyToMany(mappedBy = "borrowedBooks")
//    @JsonBackReference
//    private List<User> borrowedBy;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<BorrowedBook> borrowedBooks;

    // Campo calculado (não será guardado na base de dados)
    @Transient
    @JsonIgnore     // Evita serialização infinita
    public Integer getAvailableCopies() {
        return copies - (borrowedBooks != null ? borrowedBooks.size() : 0);
    }

    // Métodos getter e setter explicitamente declarados (opcional, pois o Lombok já os gera)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public Integer getCopies() {
        return copies;
    }

    public void setCopies(Integer copies) {
        this.copies = copies;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

//    public List<User> getBorrowedBy() {
//        return borrowedBy;
//    }
//
//    public void setBorrowedBy(List<User> borrowedBy) {
//        this.borrowedBy = borrowedBy;
//    }


    public List<BorrowedBook> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void setBorrowedBooks(List<BorrowedBook> borrowedBooks) {
        this.borrowedBooks = borrowedBooks;
    }
}


