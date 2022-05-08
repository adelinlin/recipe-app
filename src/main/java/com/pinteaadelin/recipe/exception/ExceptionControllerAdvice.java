package com.pinteaadelin.recipe.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(NegativeQuantityException.class)
    public ResponseEntity<String> handle(NegativeQuantityException e) {
        return new ResponseEntity<>(e.getMessage(), BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handle(NotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), NOT_FOUND);
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<String> handle(AlreadyExistsException e) {
        return new ResponseEntity<>(e.getMessage(), BAD_REQUEST);
    }

    @ExceptionHandler(UserRoleNotSupportedException.class)
    public ResponseEntity<String> handle(UserRoleNotSupportedException e) {
        return new ResponseEntity<>(e.getMessage(), BAD_REQUEST);
    }

}
