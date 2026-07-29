package com.backend.project.interfaces.dto.user;

import com.backend.project.domain.model.Role;

import java.time.Instant;
import java.util.UUID;

public record UserRequestDTO (
    @jakarta.validation.constraints.NotBlank
    @jakarta.validation.constraints.Size(min = 2, max = 100)
    String name,
    @jakarta.validation.constraints.NotBlank
    @jakarta.validation.constraints.Email
    String email,
    @jakarta.validation.constraints.NotBlank
    @jakarta.validation.constraints.Size(min = 8)
    String password,
    @jakarta.validation.constraints.NotNull
    Role role
) {}
