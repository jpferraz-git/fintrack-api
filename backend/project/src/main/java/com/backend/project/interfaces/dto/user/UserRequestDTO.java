package com.backend.project.interfaces.dto.user;

import java.time.Instant;
import java.util.UUID;

public record UserRequestDTO (
    String name,
    String email,
    String password
) {}
