package com.example.project_service_api.exception;

public class ReservationNotFoundException extends RuntimeException {
    public ReservationNotFoundException(String message) {
        super(message);
    }
}
