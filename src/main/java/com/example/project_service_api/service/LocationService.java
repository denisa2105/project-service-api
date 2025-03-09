package com.example.project_service_api.service;

import com.example.project_service_api.dto.LocationDto;
import com.example.project_service_api.mapper.ObjectMapper;
import com.example.project_service_api.mapper.impl.LocationMapper;
import com.example.project_service_api.persistence.entity.Location;
import com.example.project_service_api.persistence.entity.Reservation;
import com.example.project_service_api.persistence.repository.LocationRepository;
import com.example.project_service_api.persistence.repository.ReservationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LocationService {

    private final LocationRepository locationRepository;
//    private final ReservationRepository reservationRepository;
    private final ObjectMapper<LocationDto, Location> locationMapper;

    public LocationService(LocationRepository locationRepository,
                           ObjectMapper<LocationDto, Location> locationMapper) {
        this.locationRepository = locationRepository;
        this.locationMapper = locationMapper;
    }

    public List<LocationDto> getLocationsByName(String name) {
        List<Location> locationsByName = locationRepository.getLocationsByName(name);

        return locationsByName.stream()
                .map(locationMapper::mapEntityToDto)
                .toList();
    }

    public List<LocationDto> getAllLocations() {
        List<Location> allLocations = locationRepository.findAll();

        return allLocations.stream()
                .map(locationMapper::mapEntityToDto)
                .toList();
    }

    public void createLocation(LocationDto locationDto) {
        Location location = locationMapper.mapDtoToEntity(locationDto);
        locationRepository.save(location);
    }

//    public void updateLocation(UUID id, LocationDto locationDto) {
//        Optional<Location> existingLocation = locationRepository.findById(id);
//
//        if (existingLocation == null) {
//            throw new RuntimeException("Location not found with id: " + id);
//        }
//
//        // 🔹 Actualizăm doar câmpurile transmise
//        if (locationDto.getName() != null) {
//            existingLocation.setName(locationDto.getName());
//        }
//
//        if (locationDto.getCapacity() > 0) {
//            existingLocation.setCapacity(locationDto.getCapacity());
//        }
//
//        if (locationDto.getAddress() != null) {
//            existingLocation.setAddress(locationDto.getAddress());
//        }
//
//        // 🔹 Actualizăm lista de rezervări doar dacă există reservationIds
//        if (locationDto.getReservationIds() != null && !locationDto.getReservationIds().isEmpty()) {
//            List<Reservation> updatedReservations = locationDto.getReservationIds().stream()
//                    .map(reservationId -> {
//                        Reservation reservation = reservationRepository.findById(reservationId);
//                        if (reservation == null) {
//                            throw new RuntimeException("Reservation not found with id: " + reservationId);
//                        }
//                        return reservation;
//                    })
//                    .toList();

//            existingLocation.setReservations(updatedReservations);
//        }

//        // 🔹 Salvăm locația actualizată
//        locationRepository.save(existingLocation);

//    }

    public void deleteLocationById(UUID id) {
        locationRepository.deleteById(id);
    }
}
