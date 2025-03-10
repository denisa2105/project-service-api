package com.example.project_service_api.service;

import com.example.project_service_api.dto.UserDto;
import com.example.project_service_api.exception.UserNotFoundException;
import com.example.project_service_api.mapper.ObjectMapper;
import com.example.project_service_api.persistence.entity.User;
import com.example.project_service_api.persistence.repository.UserRepository;
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
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ObjectMapper<UserDto, User> userMapper;

    @InjectMocks
    private UserService userService;

    private User user;
    private UserDto userDto;
    private UUID userId;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();

        user = new User();
        user.setId(userId);
        user.setUsername("testUser");
        user.setEmail("test@example.com");
        user.setPassword("password123");

        userDto = new UserDto(
                userId,
                "testUser",
                "test@example.com",
                "password123"
        );
    }

    @Test
    void testGetAllUsers() {
        when(userRepository.findAll()).thenReturn(List.of(user));
        when(userMapper.mapEntityToDto(user)).thenReturn(userDto);

        List<UserDto> result = userService.getAllUsers();

        assertEquals(1, result.size());
        assertEquals("testUser", result.get(0).getUsername());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testGetUserById_Success() {
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userMapper.mapEntityToDto(user)).thenReturn(userDto);

        UserDto result = userService.getUserById(userId);

        assertNotNull(result);
        assertEquals("testUser", result.getUsername());
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void testGetUserById_NotFound() {
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getUserById(userId));
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void testCreateUser() {
        when(userMapper.mapDtoToEntity(userDto)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);

        assertDoesNotThrow(() -> userService.createUser(userDto));
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testUpdateUser_Success() {
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDto updatedDto = new UserDto(
                userId,
                "updatedUser",
                "updated@example.com",
                "newPassword123"
        );

        assertDoesNotThrow(() -> userService.updateUser(userId, updatedDto));

        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testUpdateUser_NotFound() {
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        UserDto updatedDto = new UserDto(
                userId,
                "updatedUser",
                "updated@example.com",
                "newPassword123"
        );

        assertThrows(UserNotFoundException.class, () -> userService.updateUser(userId, updatedDto));

        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void testDeleteUser_Success() {
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        doNothing().when(userRepository).delete(user);

        assertDoesNotThrow(() -> userService.deleteUser(userId));

        verify(userRepository, times(1)).delete(user);
    }

    @Test
    void testDeleteUser_NotFound() {
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.deleteUser(userId));

        verify(userRepository, times(1)).findById(userId);
    }
}
