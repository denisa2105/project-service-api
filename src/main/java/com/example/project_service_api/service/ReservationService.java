package com.example.project_service_api.service;

import com.example.project_service_api.dto.ReservationDto;
import com.example.project_service_api.exception.ReservationNotFoundException;
import com.example.project_service_api.mapper.ObjectMapper;
import com.example.project_service_api.persistence.entity.Reservation;
import com.example.project_service_api.persistence.repository.ReservationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ObjectMapper<ReservationDto, Reservation> reservationMapper;

    public ReservationService(ReservationRepository reservationRepository, ObjectMapper<ReservationDto, Reservation> reservationMapper) {
        this.reservationRepository = reservationRepository;
        this.reservationMapper = reservationMapper;
    }

    public List<ReservationDto> getAllReservations() {
        return reservationRepository.findAll().stream()
                .map(reservationMapper::mapEntityToDto)
                .toList();
    }

    public ReservationDto getReservationById(UUID id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException("Reservation not found with id: " + id));
        return reservationMapper.mapEntityToDto(reservation);
    }

    public void createReservation(ReservationDto reservationDto) {
        Reservation reservation = reservationMapper.mapDtoToEntity(reservationDto);
        reservationRepository.save(reservation);
    }

    public void updateReservation(UUID id, ReservationDto reservationDto) {
        Reservation existingReservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException("Reservation not found with id: " + id));

        if (reservationDto.status() != null) {
            existingReservation.setStatus(reservationDto.status());
        }

        if (reservationDto.reservationDate() != null) {
            existingReservation.setReservationDate(reservationDto.reservationDate());
        }

        reservationRepository.save(existingReservation);
    }

    public void deleteReservation(UUID id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException("Reservation not found with id: " + id));

        reservationRepository.delete(reservation);
    }
}
