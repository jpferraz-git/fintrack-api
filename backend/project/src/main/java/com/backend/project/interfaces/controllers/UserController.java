package com.backend.project.interfaces.controllers;

import com.backend.project.application.service.UserService;
import com.backend.project.interfaces.dto.user.UserRequestDTO;
import com.backend.project.interfaces.dto.user.UserResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> findAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserRequestDTO user) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.create(user));
    }
}
