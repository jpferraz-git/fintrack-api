package com.backend.project.interfaces.controllers;

import static com.backend.project.interfaces.controllers.utils.Normalizer.errorResponse;


import com.backend.project.domain.utils.Result;
import com.backend.project.application.service.UserService;
import com.backend.project.interfaces.dto.user.UserRequestDTO;
import com.backend.project.interfaces.dto.user.UserResponseDTO;
import com.backend.project.interfaces.swagger.UserControllerSwagger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;

import static com.backend.project.interfaces.controllers.utils.Normalizer.resolveStatus;

@RestController
@RequestMapping("/users")
public class UserController implements UserControllerSwagger {

    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<Page<UserResponseDTO>> findAll(@PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(userService.findAll(pageable));
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserRequestDTO user) {
        Result<UserResponseDTO> result = userService.create(user);
        if (result.isOk()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(result.getValue());
        }
        return ResponseEntity.status(resolveStatus(result.getMessage())).body(errorResponse(result.getMessage()));
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody UserRequestDTO user) {
        Result<UserResponseDTO> result = userService.updateUser(user);
        if (result.isOk()) {
            return ResponseEntity.ok(result.getValue());
        }
        return ResponseEntity.status(resolveStatus(result.getMessage())).body(errorResponse(result.getMessage()));
    }

    @PutMapping("/{email}")
    public ResponseEntity<?> updateUserByEmail(@PathVariable String email, @RequestBody UserRequestDTO user) {
        Result<UserResponseDTO> result = userService.updateUserByEmail(email, user);
        if (result.isOk()) {
            return ResponseEntity.ok(result.getValue());
        }
        return ResponseEntity.status(resolveStatus(result.getMessage())).body(errorResponse(result.getMessage()));
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser(@RequestParam String email) {
        Result<Void> result = userService.deleteByEmail(email);
        if (result.isOk()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(resolveStatus(result.getMessage())).body(errorResponse(result.getMessage()));
    }

}
