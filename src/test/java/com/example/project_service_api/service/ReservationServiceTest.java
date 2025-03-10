package com.example.project_service_api.service;

import com.example.project_service_api.dto.ReservationDto;
import com.example.project_service_api.exception.ReservationNotFoundException;
import com.example.project_service_api.mapper.ObjectMapper;
import com.example.project_service_api.persistence.entity.Reservation;
import com.example.project_service_api.persistence.repository.ReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private ObjectMapper<ReservationDto, Reservation> reservationMapper;

    @InjectMocks
    private ReservationService reservationService;

    private Reservation reservation;
    private ReservationDto reservationDto;
    private UUID reservationId;

    @BeforeEach
    void setUp() {
        reservationId = UUID.randomUUID();

        reservation = new Reservation();
        reservation.setId(reservationId);
        reservation.setStatus("Confirmed");
        reservation.setReservationDate("2025-04-10");

        reservationDto = new ReservationDto(
                reservationId,
                "Confirmed",
                "2025-04-10",
                UUID.randomUUID(),  // customerId
                UUID.randomUUID(),  // locationId
                List.of(UUID.randomUUID()) // paymentIds
        );
    }

    @Test
    void testGetAllReservations() {
        when(reservationRepository.findAll()).thenReturn(List.of(reservation));
        when(reservationMapper.mapEntityToDto(reservation)).thenReturn(reservationDto);

        List<ReservationDto> result = reservationService.getAllReservations();

        assertEquals(1, result.size());
        assertEquals("Confirmed", result.get(0).status());
        verify(reservationRepository, times(1)).findAll();
    }

    @Test
    void testGetReservationById_Success() {
        when(reservationRepository.findById(reservationId)).thenReturn(Optional.of(reservation));
        when(reservationMapper.mapEntityToDto(reservation)).thenReturn(reservationDto);

        ReservationDto result = reservationService.getReservationById(reservationId);

        assertNotNull(result);
        assertEquals("Confirmed", result.status());
        verify(reservationRepository, times(1)).findById(reservationId);
    }

    @Test
    void testGetReservationById_NotFound() {
        when(reservationRepository.findById(reservationId)).thenReturn(Optional.empty());

        assertThrows(ReservationNotFoundException.class, () -> reservationService.getReservationById(reservationId));
        verify(reservationRepository, times(1)).findById(reservationId);
    }

    @Test
    void testCreateReservation() {
        when(reservationMapper.mapDtoToEntity(reservationDto)).thenReturn(reservation);
        when(reservationRepository.save(reservation)).thenReturn(reservation);

        assertDoesNotThrow(() -> reservationService.createReservation(reservationDto));
        verify(reservationRepository, times(1)).save(reservation);
    }

    @Test
    void testUpdateReservation_Success() {
        when(reservationRepository.findById(reservationId)).thenReturn(Optional.of(reservation));
        when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);

        ReservationDto updatedDto = new ReservationDto(
                reservationId,
                "Cancelled",
                "2025-05-01",
                reservationDto.customerId(),
                reservationDto.locationId(),
                reservationDto.paymentIds()
        );

        assertDoesNotThrow(() -> reservationService.updateReservation(reservationId, updatedDto));

        verify(reservationRepository, times(1)).save(reservation);
    }

    @Test
    void testUpdateReservation_NotFound() {
        when(reservationRepository.findById(reservationId)).thenReturn(Optional.empty());

        ReservationDto updatedDto = new ReservationDto(
                reservationId,
                "Cancelled",
                "2025-05-01",
                reservationDto.customerId(),
                reservationDto.locationId(),
                reservationDto.paymentIds()
        );

        assertThrows(ReservationNotFoundException.class, () -> reservationService.updateReservation(reservationId, updatedDto));

        verify(reservationRepository, times(1)).findById(reservationId);
    }

    @Test
    void testDeleteReservation_Success() {
        when(reservationRepository.findById(reservationId)).thenReturn(Optional.of(reservation));
        doNothing().when(reservationRepository).delete(reservation);

        assertDoesNotThrow(() -> reservationService.deleteReservation(reservationId));

        verify(reservationRepository, times(1)).delete(reservation);
    }

    @Test
    void testDeleteReservation_NotFound() {
        when(reservationRepository.findById(reservationId)).thenReturn(Optional.empty());

        assertThrows(ReservationNotFoundException.class, () -> reservationService.deleteReservation(reservationId));

        verify(reservationRepository, times(1)).findById(reservationId);
    }
}
