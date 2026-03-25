package com.backend.project.interfaces.dto.user;

import com.backend.project.domain.model.Role;

import java.time.Instant;
import java.util.UUID;

public record UserRequestDTO (
    String name,
    String email,
    String password,
    Role role
) {}
