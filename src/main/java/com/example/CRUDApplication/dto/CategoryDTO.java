package com.example.CRUDApplication.dto;

import com.example.CRUDApplication.model.Category;

import java.util.List;
import java.util.stream.Collectors;

//public class CategoryDTO {
//    private Long id;
//    private String name;
//    private List<BookDTO> books;
//
//    public CategoryDTO(Category category) {
//        this.id = category.getId();
//        this.name = category.getName();
//        this.books = category.getBooks().stream()
//                .map(BookDTO::new).
//                collect(Collectors.toList());
//    }
//
//    // Getters e Setters
//
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public List<BookDTO> getBooks() {
//        return books;
//    }
//
//    public void setBooks(List<BookDTO> books) {
//        this.books = books;
//    }
//}

public class CategoryDTO {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

