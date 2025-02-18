package com.example.CRUDApplication.exception;

public class RequestDataMissingException extends RuntimeException {

    public RequestDataMissingException(String message) {
        super(message);
    }
}
