package com.backend.project.exception;

public class AssetAlreadyExistsException extends RuntimeException {

    public AssetAlreadyExistsException(String ticker) {
        super("Asset with ticker '" + ticker + "' already exists.");
    }
}

