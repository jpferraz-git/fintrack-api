package com.backend.project.interfaces.controllers.utils;

import org.springframework.http.HttpStatus;

public final class Normalizer {

    private Normalizer() {}

    public static HttpStatus resolveStatus(String message) {
        if (message == null || message.isBlank()) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        String normalized = message.toLowerCase();
        if (normalized.contains("not found") || normalized.contains("no klines")) {
            return HttpStatus.NOT_FOUND;
        }
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
