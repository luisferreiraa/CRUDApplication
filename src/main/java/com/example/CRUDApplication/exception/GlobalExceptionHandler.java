package com.example.CRUDApplication.exception;

import com.example.CRUDApplication.response.ErrorResponse;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

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
}
