package com.example.CRUDApplication.exception;

public class ResourceNotAvailableException extends RuntimeException {
    public ResourceNotAvailableException(String message) {
        super(message);
    }
}
