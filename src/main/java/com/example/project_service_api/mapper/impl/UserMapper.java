package com.example.project_service_api.mapper.impl;

import com.example.project_service_api.dto.UserDto;
import com.example.project_service_api.mapper.ObjectMapper;
import com.example.project_service_api.persistence.entity.User;
import com.example.project_service_api.persistence.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class UserMapper implements ObjectMapper<UserDto, User> {

    @Override
    public User mapDtoToEntity(UserDto dto) {
        User user = new User();

        user.setId(dto.getId());
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());

        return user;
    }

    @Override
    public UserDto mapEntityToDto(User entity) {
        return new UserDto(
                entity.getId(),
                entity.getUsername(),
                entity.getEmail(),
                entity.getPassword()
        );
    }
}
