package com.example.c51diplompersonaltrainerrest.exception;

public class InvalidParametrException extends RuntimeException {

    public InvalidParametrException() {
        super();
    }

    public InvalidParametrException(String message) {
        super(message);
    }

    public InvalidParametrException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidParametrException(Throwable cause) {
        super(cause);
    }
}
