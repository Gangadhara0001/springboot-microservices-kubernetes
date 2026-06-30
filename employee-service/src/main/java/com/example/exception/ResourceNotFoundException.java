package com.example.exception;

public class ResourceNotFoundException extends EmployeeServiceBaseException{

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
