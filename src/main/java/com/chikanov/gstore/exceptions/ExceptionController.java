package com.chikanov.gstore.exceptions;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleRuntimeException(Exception e)
    {
        System.out.println("EEEE");
        return new ResponseEntity<>(e.getMessage(), HttpStatusCode.valueOf(400));
    }
}
