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
public class PaymentDto {

    private UUID id;
    private double amount;
    private String paymentMethod;
    private List<UUID> reservationIds;
}
