package com.backend.project.exception;

public class AssetAlreadyExistsException extends RuntimeException {

    public AssetAlreadyExistsException(String symbol) {
        super("Asset with symbol '" + symbol + "' already exists.");
    }
}

