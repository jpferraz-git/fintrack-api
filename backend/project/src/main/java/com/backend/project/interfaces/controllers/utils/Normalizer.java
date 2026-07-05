package com.backend.project.interfaces.controllers.utils;

import org.springframework.http.HttpStatus;

public final class Normalizer {

    private Normalizer() {}

    public static HttpStatus resolveStatus(String message) {
        if (message == null || message.isBlank()) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        String normalized = message.toLowerCase();

        if (normalized.contains("not found") || normalized.contains("no klines") || normalized.contains("does not exist")) {
            return HttpStatus.NOT_FOUND;
        }
        if (normalized.contains("already exists") || normalized.contains("already in use")) {
            return HttpStatus.CONFLICT;
        }
        if (normalized.contains("failed to retrieve")) {
            return HttpStatus.BAD_GATEWAY;
        }
        if (normalized.contains("invalid") || normalized.contains("required") || normalized.contains("must be") || normalized.contains("cannot be") || normalized.contains("blank")) {
            return HttpStatus.BAD_REQUEST;
        }

        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public static com.backend.project.interfaces.dto.error.ErrorResponseDTO errorResponse(String message) {
        return new com.backend.project.interfaces.dto.error.ErrorResponseDTO(
                resolveStatus(message).value(),
                message,
                java.time.Instant.now()
        );
    }
}
