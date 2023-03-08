package com.cvatos.chorifybackend.exception;

import org.springframework.http.HttpStatus;

public class ChorifyException extends RuntimeException {

    private HttpStatus status;
    
    // Constructor to set the message and HTTP Status
    public ChorifyException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    // Getter
    public HttpStatus getStatus() {
        return this.status;
    }
}
