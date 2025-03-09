package com.example.project_service_api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class UserDto {

    private UUID id;
    private String username;
    private String email;
    private String password;
}
