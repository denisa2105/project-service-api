package com.example.project_service_api.persistence.repository;

import com.example.project_service_api.persistence.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, UUID> {

    List<Payment> findByPaymentMethod(String paymentMethod);
}
