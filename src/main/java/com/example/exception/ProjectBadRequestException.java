package com.example.exception;

public class ProjectBadRequestException extends RuntimeException{

    public ProjectBadRequestException(String message){
        super(message);
    }
}
