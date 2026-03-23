package com.backend.project.interfaces.controllers;

import com.backend.project.application.service.UserService;
import com.backend.project.infrastructure.entity.UserEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserEntity> createUser(@RequestBody UserEntity user) {
        UserEntity newUser = userService.create(user);
        System.out.println("CHEGOU NA CONTRLLER");
        return ResponseEntity.status(201).body(newUser);
    }
}
