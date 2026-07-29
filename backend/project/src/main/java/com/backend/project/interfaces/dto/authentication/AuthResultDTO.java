package com.backend.project.interfaces.dto.authentication;

public record AuthResultDTO(
        String accessToken,
        String refreshToken,
        LoginResponseDTO loginResponse
) {
}
