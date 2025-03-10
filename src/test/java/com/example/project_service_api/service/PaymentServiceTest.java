package com.example.project_service_api.service;

import com.example.project_service_api.dto.PaymentDto;
import com.example.project_service_api.exception.PaymentNotFoundException;
import com.example.project_service_api.mapper.ObjectMapper;
import com.example.project_service_api.persistence.entity.Payment;
import com.example.project_service_api.persistence.repository.PaymentRepository;
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
class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private ObjectMapper<PaymentDto, Payment> paymentMapper;

    @InjectMocks
    private PaymentService paymentService;

    private Payment payment;
    private PaymentDto paymentDto;
    private UUID paymentId;

    @BeforeEach
    void setUp() {
        paymentId = UUID.randomUUID();

        payment = new Payment();
        payment.setId(paymentId);
        payment.setAmount(100.0);
        payment.setPaymentMethod("Credit Card");

        paymentDto = new PaymentDto();
        paymentDto.setId(paymentId);
        paymentDto.setAmount(100.0);
        paymentDto.setPaymentMethod("Credit Card");
    }

    @Test
    void testGetAllPayments() {
        when(paymentRepository.findAll()).thenReturn(List.of(payment));
        when(paymentMapper.mapEntityToDto(payment)).thenReturn(paymentDto);

        List<PaymentDto> result = paymentService.getAllPayments();

        assertEquals(1, result.size());
        assertEquals(100.0, result.get(0).getAmount());
        verify(paymentRepository, times(1)).findAll();
    }

    @Test
    void testGetPaymentById_Success() {
        when(paymentRepository.findById(paymentId)).thenReturn(Optional.of(payment));
        when(paymentMapper.mapEntityToDto(payment)).thenReturn(paymentDto);

        PaymentDto result = paymentService.getPaymentById(paymentId);

        assertNotNull(result);
        assertEquals(100.0, result.getAmount());
        verify(paymentRepository, times(1)).findById(paymentId);
    }

    @Test
    void testGetPaymentById_NotFound() {
        when(paymentRepository.findById(paymentId)).thenReturn(Optional.empty());

        assertThrows(PaymentNotFoundException.class, () -> paymentService.getPaymentById(paymentId));
        verify(paymentRepository, times(1)).findById(paymentId);
    }

    @Test
    void testCreatePayment() {
        when(paymentMapper.mapDtoToEntity(paymentDto)).thenReturn(payment);
        when(paymentRepository.save(payment)).thenReturn(payment);

        assertDoesNotThrow(() -> paymentService.createPayment(paymentDto));
        verify(paymentRepository, times(1)).save(payment);
    }

    @Test
    void testUpdatePayment_Success() {
        when(paymentRepository.findById(paymentId)).thenReturn(Optional.of(payment));
        when(paymentRepository.save(any(Payment.class))).thenReturn(payment);

        PaymentDto updatedDto = new PaymentDto();
        updatedDto.setAmount(150.0);
        updatedDto.setPaymentMethod("PayPal");

        assertDoesNotThrow(() -> paymentService.updatePayment(paymentId, updatedDto));

        verify(paymentRepository, times(1)).save(payment);
    }

    @Test
    void testUpdatePayment_NotFound() {
        when(paymentRepository.findById(paymentId)).thenReturn(Optional.empty());

        PaymentDto updatedDto = new PaymentDto();
        updatedDto.setAmount(150.0);
        updatedDto.setPaymentMethod("PayPal");

        assertThrows(PaymentNotFoundException.class, () -> paymentService.updatePayment(paymentId, updatedDto));

        verify(paymentRepository, times(1)).findById(paymentId);
    }

    @Test
    void testDeletePayment_Success() {
        when(paymentRepository.findById(paymentId)).thenReturn(Optional.of(payment));
        doNothing().when(paymentRepository).delete(payment);

        assertDoesNotThrow(() -> paymentService.deletePayment(paymentId));

        verify(paymentRepository, times(1)).delete(payment);
    }

    @Test
    void testDeletePayment_NotFound() {
        when(paymentRepository.findById(paymentId)).thenReturn(Optional.empty());

        assertThrows(PaymentNotFoundException.class, () -> paymentService.deletePayment(paymentId));

        verify(paymentRepository, times(1)).findById(paymentId);
    }
}
