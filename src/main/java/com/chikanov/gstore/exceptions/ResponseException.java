package com.chikanov.gstore.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.RestClientResponseException;

public class ResponseException extends Exception{
    public ResponseException(String str){
        super(str);
        System.out.println(str);
    }
}
