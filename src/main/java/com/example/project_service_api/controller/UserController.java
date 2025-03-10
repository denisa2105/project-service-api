package com.example.project_service_api.controller;

import com.example.project_service_api.dto.UserDto;
import com.example.project_service_api.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable UUID id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody UserDto userDto) {
        userService.createUser(userDto);
        return ResponseEntity.ok("User created successfully!");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable UUID id, @RequestBody UserDto userDto) {
        userService.updateUser(id, userDto);
        return ResponseEntity.ok("User updated successfully!");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully!");
    }
}
