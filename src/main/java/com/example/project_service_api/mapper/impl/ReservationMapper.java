package com.example.project_service_api.mapper.impl;

import com.example.project_service_api.dto.ReservationDto;
import com.example.project_service_api.mapper.ObjectMapper;
import com.example.project_service_api.persistence.entity.Customer;
import com.example.project_service_api.persistence.entity.Location;
import com.example.project_service_api.persistence.entity.Payment;
import com.example.project_service_api.persistence.entity.Reservation;
import com.example.project_service_api.persistence.repository.CustomerRepository;
import com.example.project_service_api.persistence.repository.LocationRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class ReservationMapper implements ObjectMapper<ReservationDto, Reservation> {

    private final CustomerRepository customerRepository;
    private final LocationRepository locationRepository;

    public ReservationMapper(CustomerRepository customerRepository, LocationRepository locationRepository) {
        this.customerRepository = customerRepository;
        this.locationRepository = locationRepository;
    }
    @Override
    public Reservation mapDtoToEntity(ReservationDto dto) {
        Reservation reservation = new Reservation();
        reservation.setId(dto.id());
        reservation.setStatus(dto.status());
        reservation.setReservationDate(dto.reservationDate());

        if (dto.customerId() != null) {
            Customer customer = customerRepository.findById(dto.customerId()).orElse(null);
            reservation.setCustomer(customer);
        }

        if (dto.locationId() != null); {
            Location location = locationRepository.findById(dto.locationId()).orElse(null);
            reservation.setLocation(location);
        }

        return reservation;
    }

    @Override
    public ReservationDto mapEntityToDto(Reservation entity) {
        List<UUID> paymentIds = entity.getPayments().stream()
                .map(Payment::getId)
                .toList();

        return new ReservationDto(
            entity.getId(),
            entity.getStatus(),
                entity.getReservationDate(),
                entity.getCustomer() != null ? entity.getCustomer().getId() : null,
                entity.getLocation() != null ? entity.getLocation().getId() : null,
                paymentIds
        );

    }
}
