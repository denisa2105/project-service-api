package com.example.project_service_api.service;

import com.example.project_service_api.dto.LocationDto;
import com.example.project_service_api.exception.LocationNotFoundException;
import com.example.project_service_api.mapper.ObjectMapper;
import com.example.project_service_api.persistence.entity.Location;
import com.example.project_service_api.persistence.repository.LocationRepository;
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
class LocationServiceTest {

    @Mock
    private LocationRepository locationRepository;

    @Mock
    private ObjectMapper<LocationDto, Location> locationMapper;

    @InjectMocks
    private LocationService locationService;

    private Location location;
    private LocationDto locationDto;
    private UUID locationId;

    @BeforeEach
    void setUp() {
        locationId = UUID.randomUUID();

        location = new Location();
        location.setId(locationId);
        location.setName("Test Location");
        location.setCapacity(100);
        location.setAddress("123 Test Street");

        locationDto = new LocationDto();
        locationDto.setId(locationId);
        locationDto.setName("Test Location");
        locationDto.setCapacity(100);
        locationDto.setAddress("123 Test Street");
    }

    @Test
    void testGetAllLocations() {
        when(locationRepository.findAll()).thenReturn(List.of(location));
        when(locationMapper.mapEntityToDto(location)).thenReturn(locationDto);

        List<LocationDto> result = locationService.getAllLocations();

        assertEquals(1, result.size());
        assertEquals("Test Location", result.get(0).getName());
        verify(locationRepository, times(1)).findAll();
    }

    @Test
    void testGetLocationsByName() {
        when(locationRepository.getLocationsByName("Test Location")).thenReturn(List.of(location));
        when(locationMapper.mapEntityToDto(location)).thenReturn(locationDto);

        List<LocationDto> result = locationService.getLocationsByName("Test Location");

        assertEquals(1, result.size());
        assertEquals("Test Location", result.get(0).getName());
        verify(locationRepository, times(1)).getLocationsByName("Test Location");
    }

    @Test
    void testCreateLocation() {
        when(locationMapper.mapDtoToEntity(locationDto)).thenReturn(location);
        when(locationRepository.save(location)).thenReturn(location);

        assertDoesNotThrow(() -> locationService.createLocation(locationDto));
        verify(locationRepository, times(1)).save(location);
    }

    @Test
    void testDeleteLocation_Success() {
        when(locationRepository.existsById(locationId)).thenReturn(true);
        doNothing().when(locationRepository).deleteById(locationId);

        assertDoesNotThrow(() -> locationService.deleteLocationById(locationId));

        verify(locationRepository, times(1)).existsById(locationId);
        verify(locationRepository, times(1)).deleteById(locationId);
    }

    @Test
    void testDeleteLocation_NotFound() {
        when(locationRepository.existsById(locationId)).thenReturn(false);

        assertThrows(LocationNotFoundException.class, () -> locationService.deleteLocationById(locationId));
        verify(locationRepository, times(1)).existsById(locationId);
    }
}
