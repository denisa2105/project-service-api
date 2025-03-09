package com.example.project_service_api.mapper.impl;

import com.example.project_service_api.dto.PaymentDto;
import com.example.project_service_api.mapper.ObjectMapper;
import com.example.project_service_api.persistence.entity.Payment;
import com.example.project_service_api.persistence.entity.Reservation;
import com.example.project_service_api.persistence.repository.ReservationRepository;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Component
public class PaymentMapper implements ObjectMapper<PaymentDto, Payment> {

    private final ReservationRepository reservationRepository;

    public PaymentMapper(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Override
    public Payment mapDtoToEntity(PaymentDto dto) {
        Payment payment = new Payment();
        payment.setId(dto.getId());
        payment.setAmount(dto.getAmount());
        payment.setPaymentMethod(dto.getPaymentMethod());

        if (dto.getReservationIds() != null) {
            List<Reservation> reservations = dto.getReservationIds().stream()
                    .map(reservationId -> reservationRepository.findById(reservationId).orElse(null))
                    .toList();
            payment.setReservations(reservations);
        }

        return payment;
    }

    @Override
    public PaymentDto mapEntityToDto(Payment entity) {
        List<UUID> reservationIds = entity.getReservations().stream()
                .map(Reservation::getId)
                .toList();

        return new PaymentDto(
                entity.getId(),
                entity.getAmount(),
                entity.getPaymentMethod(),
                reservationIds
        );
    }
}
