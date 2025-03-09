package com.example.project_service_api.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, name = "customer_name")
    private String name;

    private String email;

    private String phone;

    @OneToMany(mappedBy = "customer")
    private List<Reservation> reservations;

}
