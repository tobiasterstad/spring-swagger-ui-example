package com.example.engine.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "The item already exists")
public class AlreadyExistsException extends Exception {
    public AlreadyExistsException(int id) {
        super("Item already exists: "+id);
    }
}
