package com.backend.project.exception;

import java.util.UUID;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String email) {
        super("User with email '" + email + "' does not exist.");
    }

    public UserNotFoundException(UUID userId) {
        super("User with id '" + userId + "' does not exist.");
    }
}

