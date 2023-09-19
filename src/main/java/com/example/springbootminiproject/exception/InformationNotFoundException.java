package com.example.springbootminiproject.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**When InformationNotFoundException is thrown,  a response code of 404 not found is displayed, indicating that the information requested was not found.*/
@ResponseStatus(HttpStatus.NOT_FOUND)
public class InformationNotFoundException extends RuntimeException{
    public InformationNotFoundException(String message) {
        super(message);
    }
}
