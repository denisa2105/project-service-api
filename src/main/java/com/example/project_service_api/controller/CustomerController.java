package com.example.project_service_api.controller;


import com.example.project_service_api.dto.CustomerDto;
import com.example.project_service_api.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<List<CustomerDto>> getAllCustomers() {
        return ResponseEntity.ok(customerService.getAllCustomers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDto> getCustomer(@PathVariable UUID id) {
        CustomerDto customerDtoById = customerService.getCustomerById(id);
        if (customerDtoById == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(customerDtoById);
    }

    @PostMapping
    public ResponseEntity<String> createCustomer(@RequestBody CustomerDto customerDto) {
        customerService.createCustomer(customerDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Customer created successfully!");
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerDto> updateCustomer(@PathVariable UUID id,
                                                      @RequestBody CustomerDto customerDto) {
        CustomerDto updatedCustomer = customerService.updateCustomer(id, customerDto);
        return updatedCustomer != null ? ResponseEntity.ok(updatedCustomer) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable UUID id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }

}
