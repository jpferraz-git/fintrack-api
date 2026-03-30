package com.backend.project.exception;

import com.backend.project.domain.model.Role;

public class RoleNotFoundException extends RuntimeException {

    public RoleNotFoundException(Role role) {
        super("Role '" + role + "' does not exist.");
    }
}

