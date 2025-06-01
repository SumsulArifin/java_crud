package com.example.crud_postgresql.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT) // Spring will map this to 409
public class DuplicateCategoryNameException extends RuntimeException {
    public DuplicateCategoryNameException(String message) {
        super(message);
    }
}