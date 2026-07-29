package com.backend.project.interfaces.dto.authentication;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

public record LoginResponseDTO(
        LoginUserResponseDTO user) {

    public record LoginUserResponseDTO(
            String name,
            String email,
            @JsonProperty("created_at") Instant createdAt,
            @JsonProperty("updated_at") Instant updatedAt) {
    }
}
