package com.example.project_service_api.controller;

import com.example.project_service_api.dto.ReservationDto;
import com.example.project_service_api.mapper.ObjectMapper;
import com.example.project_service_api.persistence.entity.Reservation;
import com.example.project_service_api.service.ReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public ResponseEntity<List<ReservationDto>> getAllReservations() {
        return ResponseEntity.ok(reservationService.getAllReservations());
    }


    @GetMapping("/{id}")
    public ResponseEntity<ReservationDto> getReservationById(@PathVariable UUID id) {
        return ResponseEntity.ok(reservationService.getReservationById(id));
    }


    @PostMapping
    public ResponseEntity<String> createReservation(@RequestBody ReservationDto reservationDto) {
        reservationService.createReservation(reservationDto);
        return ResponseEntity.ok("Reservation created successfully!");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateReservation(@PathVariable UUID id, @RequestBody ReservationDto reservationDto) {
        reservationService.updateReservation(id, reservationDto);
        return ResponseEntity.ok("Reservation updated successfully!");
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReservation(@PathVariable UUID id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.ok("Reservation deleted successfully!");
    }
}
