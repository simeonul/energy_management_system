package com.simeon.webservices.emsusers.exceptions.models;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicateValueException extends RuntimeException{

    private static final String MESSAGE = "Value is already used: ";
    public DuplicateValueException(String message){
        super(MESSAGE + message);
    }

}
