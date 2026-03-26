package com.backend.project.exception;

public class MissingJwtTokenException extends RuntimeException {

    public MissingJwtTokenException() {
        super("JWT token is missing. Please provide the token in the Authorization header with Bearer prefix.");
    }

    public MissingJwtTokenException(String message) {
        super(message);
    }
}

