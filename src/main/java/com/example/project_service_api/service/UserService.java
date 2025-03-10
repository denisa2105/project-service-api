package com.example.project_service_api.service;

import com.example.project_service_api.dto.UserDto;
import com.example.project_service_api.exception.UserNotFoundException;
import com.example.project_service_api.mapper.ObjectMapper;
import com.example.project_service_api.persistence.entity.User;
import com.example.project_service_api.persistence.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final ObjectMapper<UserDto, User> userMapper;

    public UserService(UserRepository userRepository, ObjectMapper<UserDto, User> userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::mapEntityToDto)
                .toList();
    }

    public UserDto getUserById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
        return userMapper.mapEntityToDto(user);
    }

    public void createUser(UserDto userDto) {
        User user = userMapper.mapDtoToEntity(userDto);
        userRepository.save(user);
    }

    public void updateUser(UUID id, UserDto userDto) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));

        if (userDto.getUsername() != null) {
            existingUser.setUsername(userDto.getUsername());
        }

        if (userDto.getEmail() != null) {
            existingUser.setEmail(userDto.getEmail());
        }

        if (userDto.getPassword() != null) {
            existingUser.setPassword(userDto.getPassword());
        }

        userRepository.save(existingUser);
    }

    public void deleteUser(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));

        userRepository.delete(user);
    }
}
