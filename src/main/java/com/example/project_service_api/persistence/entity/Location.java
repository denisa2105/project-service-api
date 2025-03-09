package com.example.project_service_api.persistence.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, name = "location_name")
    private String name;
    private String address;
    private int capacity;

    @OneToMany(mappedBy = "location")
    private List<Reservation> reservations;
}
