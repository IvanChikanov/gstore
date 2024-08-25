package com.chikanov.gstore.exceptions;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<?> handleRuntimeException(Exception e)
    {
        return new ResponseEntity<>(e.getMessage(), HttpStatusCode.valueOf(404));
    }
}
