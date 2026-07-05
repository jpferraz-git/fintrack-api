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

        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
