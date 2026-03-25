package com.backend.project.interfaces.dto.register;

import com.backend.project.domain.model.Role;

public record RegisterDTO (
        String email,
        String password,
        Role role
){
}
