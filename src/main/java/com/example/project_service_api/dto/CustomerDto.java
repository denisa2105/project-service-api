package com.example.project_service_api.dto;


import com.example.project_service_api.persistence.entity.Reservation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {

    private UUID id;
    private String name;
    private String email;
    private String phone;
    private List<UUID> reservationIds;
}
