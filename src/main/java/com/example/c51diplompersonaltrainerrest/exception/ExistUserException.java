package com.example.c51diplompersonaltrainerrest.exception;

public class ExistUserException extends RuntimeException {

    public ExistUserException() {
        super();
    }

    public ExistUserException(String message) {
        super(message);
    }

    public ExistUserException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExistUserException(Throwable cause) {
        super(cause);
    }
}
