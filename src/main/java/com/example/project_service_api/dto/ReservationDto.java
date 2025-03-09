package com.example.project_service_api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;


public record ReservationDto(UUID id,
                             String status,
                             String reservationDate,
                             UUID customerId,
                             UUID locationId,
                             List<UUID> paymentIds
                             ) {

}
