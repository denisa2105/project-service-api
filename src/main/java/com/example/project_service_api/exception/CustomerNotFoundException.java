package com.example.project_service_api.exception;


public class CustomerNotFoundException extends RuntimeException {

    public CustomerNotFoundException(String message) {
        super(message);
    }
}