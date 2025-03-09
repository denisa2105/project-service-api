package com.example.project_service_api.persistence.repository;

import com.example.project_service_api.persistence.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LocationRepository extends JpaRepository<Location, UUID> {
    Optional<Location> findById(UUID id);

    List<Location> getLocationsByName(String name);

    @Query("SELECT l FROM Location l where l.name = ?1 and l.capacity = ?2")
    List<Location> findLocationsByNameAndCapacityJpql(String name, int capacity);

    @Query(value = "SELECT * FROM location WHERE location_name = ?1 and capacity = ?2", nativeQuery = true)
    List<Location> findProductsByNameAndCategoryNative(String name, int capacity);
}
