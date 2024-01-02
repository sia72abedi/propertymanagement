package com.hostfully.propertymanagement.exceptions;

public class InternalServerException extends RuntimeException {
    private static final long serialVersionUID = 4L;

    public InternalServerException(String message) {
        super(message);
    }
}
