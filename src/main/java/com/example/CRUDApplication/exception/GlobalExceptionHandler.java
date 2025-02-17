package com.example.CRUDApplication.exception;

import com.example.CRUDApplication.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<?> handleBookNotFoundException(BookNotFoundException exception) {
        ErrorResponse booksNotFound = new ErrorResponse(
                LocalDateTime.now(), exception.getMessage(), "Book List is empty");
        return new ResponseEntity<>(booksNotFound, HttpStatus.NOT_FOUND);
    }
}
