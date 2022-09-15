package com.example.exception;

public class EmailBadRequestException extends RuntimeException{


    public EmailBadRequestException(String message) {
        super(message);
    }
}
