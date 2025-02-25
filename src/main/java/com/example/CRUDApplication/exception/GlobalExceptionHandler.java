package com.example.CRUDApplication.exception;

import com.example.CRUDApplication.response.ErrorResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<?> handleBookNotFoundException(ObjectNotFoundException exception) {
        ErrorResponse booksNotFound = new ErrorResponse(
                LocalDateTime.now(),
                exception.getMessage(),
                "No object found in database ");
        return new ResponseEntity<>(booksNotFound, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RequestDataMissingException.class)
    public ResponseEntity<?> handleRequestDataMissingException(RequestDataMissingException exception) {
        ErrorResponse dataMissing = new ErrorResponse(
                LocalDateTime.now(),
                exception.getMessage(),
                "Required request data is missing"
        );
        return new ResponseEntity<>(dataMissing, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RecordAlreadyExistsException.class)
    public ResponseEntity<?> handleRecordAlreadyExistsException(RecordAlreadyExistsException exception) {
        ErrorResponse alreadyExists = new ErrorResponse(
                LocalDateTime.now(),
                exception.getMessage(),
                "Record already exists in the system"
        );
        return new ResponseEntity<>(alreadyExists, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotAvailableException.class)
    public ResponseEntity<?> handleResourceNotAvailableException(ResourceNotAvailableException exception) {
        ErrorResponse notAvailable = new ErrorResponse(
                LocalDateTime.now(),
                exception.getMessage(),
                "Resource not available in the system"
        );
        return new ResponseEntity<>(notAvailable, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericExpression(Exception exception) {
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                "Internal server error",
                exception.getMessage()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException exception) {
        // Extrai os erros de validação e junta numa única string
        String errorMessages = exception.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", ")); // Erros separados por vírgula

        // Cria a resposta de erro detalhada
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                "Validation failed",
                errorMessages
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDuplicate(DataIntegrityViolationException exception) {
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                "Data record cannot be duplicate",
                "Data integrity error"
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
