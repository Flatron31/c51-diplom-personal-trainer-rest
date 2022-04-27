package com.example.c51diplompersonaltrainerrest.controller;

import com.example.c51diplompersonaltrainerrest.exception.ExistUserException;
import com.example.c51diplompersonaltrainerrest.exception.InvalidParametrException;
import com.example.c51diplompersonaltrainerrest.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@PropertySource("classpath:msg.properties")
public class ExceptionController extends ResponseEntityExceptionHandler {

    @Value("${InvalidInputParametr}")
    private String msgInvalidInput;

    @Value("${NotFound}")
    private String msgNotFound;

    @Value("${ExistsUser}")
    private String msgExistsUser;

    @ExceptionHandler(InvalidParametrException.class)
    public ResponseEntity<Object> invalidInputException(InvalidParametrException ex) {
        return new ResponseEntity(msgInvalidInput, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> userNotFoundException(NotFoundException ex) {
        return new ResponseEntity(msgNotFound, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ExistUserException.class)
    public ResponseEntity<Object> userExistsException(ExistUserException ex) {
        return new ResponseEntity(msgExistsUser, HttpStatus.CONFLICT);
    }
}
