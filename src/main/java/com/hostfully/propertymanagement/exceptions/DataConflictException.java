package com.hostfully.propertymanagement.exceptions;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NO_CONTENT)
public class DataConflictException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public DataConflictException(String message) {
        super(message);
    }
}