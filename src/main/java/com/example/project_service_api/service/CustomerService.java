package com.example.project_service_api.service;

import com.example.project_service_api.dto.CustomerDto;
import com.example.project_service_api.mapper.impl.CustomerMapper;
import com.example.project_service_api.persistence.entity.Customer;
import com.example.project_service_api.persistence.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerService(CustomerRepository customerRepository, CustomerMapper customerMapper, CustomerMapper customerMapper1) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper1;
    }

    public List<CustomerDto> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(customerMapper::mapEntityToDto)
                .toList();
    }

    public CustomerDto getCustomerById(UUID id) {
        Optional<Customer> customer = customerRepository.findById(id);
        return customer.map(customerMapper::mapEntityToDto).orElse(null);
    }

    public CustomerDto createCustomer(CustomerDto customerDto) {
        Customer customer = customerMapper.mapDtoToEntity(customerDto);
        Customer savedCustomer = customerRepository.save(customer);
        return customerMapper.mapEntityToDto(savedCustomer);
    }

    public CustomerDto updateCustomer(UUID id, CustomerDto customerDto) {
        Optional<Customer> existingCustomer = customerRepository.findById(id);

        if (existingCustomer.isPresent()) {
            Customer customer = existingCustomer.get();
            customer.setName(customerDto.getName());
            customer.setEmail(customerDto.getEmail());
            customer.setPhone(customerDto.getPhone());

            Customer updatedCustomer = customerRepository.save(customer);
            return customerMapper.mapEntityToDto(updatedCustomer);
        }

        return null;
    }

    public void deleteCustomer(UUID id) {
        customerRepository.deleteById(id);
    }
}
