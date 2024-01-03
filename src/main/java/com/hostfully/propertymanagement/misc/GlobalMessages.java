package com.hostfully.propertymanagement.misc;

import com.hostfully.propertymanagement.exceptions.InternalServerException;

public class GlobalMessages {
    private GlobalMessages() {
        throw new InternalServerException("Util Class");
    }

    public static final String SUCCESS = "Request Processed Successfully.";
}
