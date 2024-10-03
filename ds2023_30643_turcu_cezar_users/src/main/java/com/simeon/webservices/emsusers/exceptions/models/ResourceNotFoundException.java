package com.simeon.webservices.emsusers.exceptions.models;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{
    private static final String MESSAGE = "Could not find resource: ";

    public ResourceNotFoundException(String message){
        super(MESSAGE + message);
    }
}
