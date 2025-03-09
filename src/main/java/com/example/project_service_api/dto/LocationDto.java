package com.example.project_service_api.dto;

import com.example.project_service_api.persistence.entity.Reservation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class LocationDto {

    private UUID id;
    private String name;
    private String address;
    private int capacity;
    private List<UUID> reservationIds;
}
