package com.hostfully.propertymanagement.exceptions;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NO_CONTENT)
public class InvalidInputException extends RuntimeException {
    private static final long serialVersionUID = 3L;

    public InvalidInputException(String message) {
        super(message);
    }
}