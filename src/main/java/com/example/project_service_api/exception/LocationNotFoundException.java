package com.example.project_service_api.exception;

public class LocationNotFoundException extends RuntimeException {
    public LocationNotFoundException(String message) {
        super(message);
    }
}