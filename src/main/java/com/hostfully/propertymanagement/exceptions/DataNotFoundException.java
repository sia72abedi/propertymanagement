package com.hostfully.propertymanagement.exceptions;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NO_CONTENT)
public class DataNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 2L;
    public DataNotFoundException(String message) {
        super(message);
    }

}