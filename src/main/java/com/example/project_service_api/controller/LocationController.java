package com.example.project_service_api.controller;


import com.example.project_service_api.dto.LocationDto;
import com.example.project_service_api.service.LocationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/locations")
public class LocationController {

    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping
    public ResponseEntity<List<LocationDto>> getAllLocations() {
        return ResponseEntity.ok(locationService.getAllLocations());
    }

    @GetMapping("/{name}")
    public ResponseEntity<List<LocationDto>> getLocationsByName(@RequestParam String name) {
        return ResponseEntity.ok(locationService.getLocationsByName(name));
    }

    @PostMapping
    public ResponseEntity<String> createLocation(@RequestBody LocationDto locationDto) {
        locationService.createLocation(locationDto);
        return ResponseEntity.ok("Location created successfully!");
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<String> updateLocation(@PathVariable UUID id, @RequestBody LocationDto locationDto) {
//        locationService.updateLocation(id, locationDto);
//        return ResponseEntity.ok("Location updated successfully!");
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteLocation(@PathVariable UUID id) {
        locationService.deleteLocationById(id);
        return ResponseEntity.ok("Location deleted successfully!");
    }

}
