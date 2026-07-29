package com.backend.project.interfaces.dto.register;

import com.backend.project.domain.model.Role;

public record RegisterDTO (
        String name,
        String email,
        String password
){
}
