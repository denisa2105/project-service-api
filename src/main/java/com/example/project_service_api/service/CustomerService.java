package com.example.project_service_api.service;

import com.example.project_service_api.dto.CustomerDto;
import com.example.project_service_api.exception.CustomerNotFoundException;
import com.example.project_service_api.mapper.impl.CustomerMapper;
import com.example.project_service_api.persistence.entity.Customer;
import com.example.project_service_api.persistence.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerService(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    public List<CustomerDto> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(customerMapper::mapEntityToDto)
                .toList();
    }

    public CustomerDto getCustomerById(UUID id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id: " + id));
        return customerMapper.mapEntityToDto(customer);
    }

    public CustomerDto createCustomer(CustomerDto customerDto) {
        Customer customer = customerMapper.mapDtoToEntity(customerDto);
        Customer savedCustomer = customerRepository.save(customer);
        return customerMapper.mapEntityToDto(savedCustomer);
    }

    public CustomerDto updateCustomer(UUID id, CustomerDto customerDto) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id: " + id));

        if (customerDto.getName() != null) {
            customer.setName(customerDto.getName());
        }
        if (customerDto.getEmail() != null) {
            customer.setEmail(customerDto.getEmail());
        }
        if (customerDto.getPhone() != null) {
            customer.setPhone(customerDto.getPhone());
        }

        Customer updatedCustomer = customerRepository.save(customer);
        return customerMapper.mapEntityToDto(updatedCustomer);
    }

    public void deleteCustomer(UUID id) {
        if (!customerRepository.existsById(id)) {
            throw new CustomerNotFoundException("Customer not found with id: " + id);
        }
        customerRepository.deleteById(id);
    }
}
