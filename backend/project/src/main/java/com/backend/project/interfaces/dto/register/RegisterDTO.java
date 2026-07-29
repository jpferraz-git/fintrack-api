package com.backend.project.interfaces.dto.register;

import com.backend.project.domain.model.Role;

public record RegisterDTO (
        String name,
        String email,
        @jakarta.validation.constraints.NotBlank
        @jakarta.validation.constraints.Size(min = 8)
        @jakarta.validation.constraints.Pattern(
                regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).{8,}$",
                message = "Password must contain at least one digit, one lowercase, one uppercase, one special character, and be at least 8 characters long"
        )
        String password
){
}
