package com.example.project_service_api.exception;

public class PaymentNotFoundException extends RuntimeException {
    public PaymentNotFoundException(String message) {
        super(message);
    }
}