package com.example.CRUDApplication.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class BookTitleDTO {
    @NotNull(message = "Book title cannot be null")
    @NotEmpty(message = "Book title cannot be empty")
    @NotBlank(message = "Book title cannot be blank")
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
