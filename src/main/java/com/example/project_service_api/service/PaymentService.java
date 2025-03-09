package com.example.project_service_api.service;


import com.example.project_service_api.dto.PaymentDto;
import com.example.project_service_api.mapper.ObjectMapper;
import com.example.project_service_api.persistence.entity.Payment;
import com.example.project_service_api.persistence.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final ObjectMapper<PaymentDto, Payment> paymentMapper;

    public PaymentService(PaymentRepository paymentRepository, ObjectMapper<PaymentDto, Payment> paymentMapper) {
        this.paymentRepository = paymentRepository;
        this.paymentMapper = paymentMapper;
    }

    public List<PaymentDto> getAllPayments() {
        return paymentRepository.findAll().stream()
                .map(paymentMapper::mapEntityToDto)
                .toList();
    }

    public PaymentDto getPaymentById(UUID id) {
        Payment payment = paymentRepository.findById(id).orElse(null);
        return paymentMapper.mapEntityToDto(payment);
    }

    public void createPayment(PaymentDto paymentDto) {
        Payment payment = paymentMapper.mapDtoToEntity(paymentDto);
        paymentRepository.save(payment);
    }

    public void updatePayment(UUID id, PaymentDto paymentDto) {
        Payment existingPayment = paymentRepository.findById(id).orElse(null);
        if (existingPayment == null) {
            throw new RuntimeException("Payment not found with id: " + id);
        }

        if (paymentDto.getAmount() > 0) {
            existingPayment.setAmount(paymentDto.getAmount());
        }

        if (paymentDto.getPaymentMethod() != null) {
            existingPayment.setPaymentMethod(paymentDto.getPaymentMethod());
        }

        paymentRepository.save(existingPayment);
    }

    public void deletePayment(UUID id) {
        Payment payment = paymentRepository.findById(id).orElse(null);
        if (payment == null) {
            throw new RuntimeException("Payment not found with id: " + id);
        }
        paymentRepository.delete(payment);
    }

}


