package com.example.project_service_api.mapper.impl;

import com.example.project_service_api.dto.CustomerDto;
import com.example.project_service_api.mapper.ObjectMapper;
import com.example.project_service_api.persistence.entity.Customer;
import com.example.project_service_api.persistence.entity.Reservation;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Component
public class CustomerMapper implements ObjectMapper<CustomerDto, Customer> {

    @Override
    public Customer mapDtoToEntity(CustomerDto dto) {
        Customer customer = new Customer();

        customer.setId(dto.getId());
        customer.setName(dto.getName());
        customer.setEmail(dto.getEmail());
        customer.setPhone(dto.getPhone());

        return customer;
    }

    @Override
    public CustomerDto mapEntityToDto(Customer entity) {
        CustomerDto dto = new CustomerDto();

        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setEmail(entity.getEmail());
        dto.setPhone(entity.getPhone());

        List<UUID> reservationsIds = entity.getReservations().stream()
                .map(Reservation::getId)
                .toList();

        dto.setReservationIds(reservationsIds);

        return dto;
    }
}
