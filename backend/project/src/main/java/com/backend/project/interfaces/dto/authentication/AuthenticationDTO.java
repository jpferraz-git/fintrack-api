package com.backend.project.interfaces.dto.authentication;

public record AuthenticationDTO(
        @jakarta.validation.constraints.NotBlank
        @jakarta.validation.constraints.Email
        String email,
        @jakarta.validation.constraints.NotBlank
        String password
) {
}
