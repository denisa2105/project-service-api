package com.example.project_service_api.service;

import com.example.project_service_api.dto.LocationDto;
import com.example.project_service_api.exception.LocationNotFoundException;
import com.example.project_service_api.mapper.ObjectMapper;
import com.example.project_service_api.persistence.entity.Location;
import com.example.project_service_api.persistence.repository.LocationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LocationService {

    private final LocationRepository locationRepository;
    private final ObjectMapper<LocationDto, Location> locationMapper;

    public LocationService(LocationRepository locationRepository, ObjectMapper<LocationDto, Location> locationMapper) {
        this.locationRepository = locationRepository;
        this.locationMapper = locationMapper;
    }

    public List<LocationDto> getAllLocations() {
        return locationRepository.findAll().stream()
                .map(locationMapper::mapEntityToDto)
                .toList();
    }

    public List<LocationDto> getLocationsByName(String name) {
        return locationRepository.getLocationsByName(name).stream()
                .map(locationMapper::mapEntityToDto)
                .toList();
    }

    public void createLocation(LocationDto locationDto) {
        Location location = locationMapper.mapDtoToEntity(locationDto);
        locationRepository.save(location);
    }

    public void deleteLocationById(UUID id) {
        if (!locationRepository.existsById(id)) {
            throw new LocationNotFoundException("Location not found with ID: " + id);
        }
        locationRepository.deleteById(id);
    }
}
