package com.backend.project.interfaces.dto.user;

import com.backend.project.domain.model.Role;

import java.time.Instant;
import java.util.UUID;

public record UserResponseDTO(
    UUID userId,
    String name,
    String email,
    Role role,
    Instant createdAt,
    Instant updatedAt
)
{}
