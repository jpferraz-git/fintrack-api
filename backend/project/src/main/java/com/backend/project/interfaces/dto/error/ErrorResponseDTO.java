package com.backend.project.interfaces.dto.error;

import java.time.Instant;

public record ErrorResponseDTO(
        int status,
        String message,
        Instant timestamp
) {}
