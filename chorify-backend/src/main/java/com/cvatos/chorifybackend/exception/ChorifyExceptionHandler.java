package com.cvatos.chorifybackend.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ChorifyExceptionHandler extends ResponseEntityExceptionHandler  {

    @ExceptionHandler(ChorifyException.class)
    public ResponseEntity<String> handleChorifyException(ChorifyException exception) {
		return new ResponseEntity<>(exception.getMessage(), exception.getStatus());
	}

    
}
