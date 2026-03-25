package com.backend.project.interfaces.dto.authentication;

public record AuthenticationDTO(
        String email,
        String password
) {
}
