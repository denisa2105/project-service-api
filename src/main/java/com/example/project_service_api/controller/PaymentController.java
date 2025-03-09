package com.example.project_service_api.controller;

import com.example.project_service_api.dto.PaymentDto;
import com.example.project_service_api.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping
    public ResponseEntity<List<PaymentDto>> getAllPayments() {
        return ResponseEntity.ok(paymentService.getAllPayments());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentDto> getPaymentById(@PathVariable UUID id) {
        return ResponseEntity.ok(paymentService.getPaymentById(id));
    }

    @PostMapping
    public ResponseEntity<String> createPayment(@RequestBody PaymentDto paymentDto) {
        paymentService.createPayment(paymentDto);
        return ResponseEntity.ok("Payment created successfully!");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updatePayment(@PathVariable UUID id, @RequestBody PaymentDto paymentDto) {
        paymentService.updatePayment(id, paymentDto);
        return ResponseEntity.ok("Payment updated successfully!");
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePayment(@PathVariable UUID id) {
        paymentService.deletePayment(id);
        return ResponseEntity.ok("Payment deleted successfully!");
    }
}
