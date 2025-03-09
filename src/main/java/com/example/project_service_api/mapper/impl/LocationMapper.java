package com.example.project_service_api.mapper.impl;


import com.example.project_service_api.dto.CustomerDto;
import com.example.project_service_api.dto.LocationDto;
import com.example.project_service_api.mapper.ObjectMapper;
import com.example.project_service_api.persistence.entity.Customer;
import com.example.project_service_api.persistence.entity.Location;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class LocationMapper implements ObjectMapper<LocationDto, Location> {


    @Override
    public Location mapDtoToEntity(LocationDto dto) {
        Location location = new Location();
        location.setId(dto.getId());
        location.setName(dto.getName());
        location.setAddress(dto.getAddress());
        location.setCapacity(dto.getCapacity());
        // Rezervările nu sunt setate aici pentru că DTO-ul nu conține obiecte Reservation

        return location;
    }

    @Override
    public LocationDto mapEntityToDto(Location entity) {
        List<UUID> reservationIds = entity.getReservations().stream()
                .map(reservation -> reservation.getId())
                .toList();

        return new LocationDto(
                entity.getId(),
                entity.getName(),
                entity.getAddress(),
                entity.getCapacity(),
                reservationIds
        );

    }
}
