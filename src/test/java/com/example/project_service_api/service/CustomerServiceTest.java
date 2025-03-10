package com.example.project_service_api.service;

import com.example.project_service_api.dto.CustomerDto;
import com.example.project_service_api.exception.CustomerNotFoundException;
import com.example.project_service_api.mapper.ObjectMapper;
import com.example.project_service_api.mapper.impl.CustomerMapper;
import com.example.project_service_api.persistence.entity.Customer;
import com.example.project_service_api.persistence.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CustomerMapper customerMapper;

    @InjectMocks
    private CustomerService customerService;

    private Customer customer;
    private CustomerDto customerDto;
    private UUID customerId;

    @BeforeEach
    void setUp() {
        customerId = UUID.randomUUID();
        customer = new Customer();
        customer.setId(customerId);
        customer.setName("John Doe");
        customer.setEmail("johndoe@example.com");
        customer.setPhone("0765432100");

        customerDto = new CustomerDto();
        customerDto.setId(customerId);
        customerDto.setName("John Doe");
        customerDto.setEmail("johndoe@example.com");
        customerDto.setPhone("0765432100");
        customerDto.setReservationIds(List.of());
    }

    @Test
    void testGetAllCustomers() {
        when(customerRepository.findAll()).thenReturn(List.of(customer));
        when(customerMapper.mapEntityToDto(customer)).thenReturn(customerDto);

        List<CustomerDto> result = customerService.getAllCustomers();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("John Doe", result.get(0).getName());
    }

    @Test
    void testGetCustomerById_Found() {
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        when(customerMapper.mapEntityToDto(customer)).thenReturn(customerDto);

        CustomerDto result = customerService.getCustomerById(customerId);

        assertNotNull(result);
        assertEquals("John Doe", result.getName());
    }

    @Test
    void testGetCustomerById_NotFound() {
        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(CustomerNotFoundException.class, () ->
                customerService.getCustomerById(customerId));

        assertEquals("Customer not found with id: " + customerId, exception.getMessage());
    }

    @Test
    void testCreateCustomer() {
        when(customerMapper.mapDtoToEntity(customerDto)).thenReturn(customer);
        when(customerRepository.save(customer)).thenReturn(customer);
        when(customerMapper.mapEntityToDto(customer)).thenReturn(customerDto);

        CustomerDto result = customerService.createCustomer(customerDto);

        assertNotNull(result);
        assertEquals("John Doe", result.getName());
        verify(customerRepository, times(1)).save(customer);
    }

    @Test
    void testUpdateCustomer_Success() {
        CustomerDto updatedDto = new CustomerDto();
        updatedDto.setId(customerId);
        updatedDto.setName("John Smith");
        updatedDto.setEmail("johnsmith@example.com");
        updatedDto.setPhone("0712345678");

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);
        when(customerMapper.mapEntityToDto(any(Customer.class))).thenReturn(updatedDto);

        CustomerDto result = customerService.updateCustomer(customerId, updatedDto);

        assertNotNull(result);
        assertEquals("John Smith", result.getName());
        assertEquals("johnsmith@example.com", result.getEmail());
        assertEquals("0712345678", result.getPhone());
        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    @Test
    void testUpdateCustomer_NotFound() {
        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(CustomerNotFoundException.class, () ->
                customerService.updateCustomer(customerId, customerDto));

        assertEquals("Customer not found with id: " + customerId, exception.getMessage());
    }

    @Test
    void testDeleteCustomer_Success() {
        when(customerRepository.existsById(customerId)).thenReturn(true);
        doNothing().when(customerRepository).deleteById(customerId);

        customerService.deleteCustomer(customerId);

        verify(customerRepository, times(1)).deleteById(customerId);
    }

    @Test
    void testDeleteCustomer_NotFound() {
        when(customerRepository.existsById(customerId)).thenReturn(false);

        Exception exception = assertThrows(CustomerNotFoundException.class, () ->
                customerService.deleteCustomer(customerId));

        assertEquals("Customer not found with id: " + customerId, exception.getMessage());
    }
}
