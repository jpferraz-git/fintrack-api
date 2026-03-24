package com.backend.project.exception;

public class AssetNotFoundException extends RuntimeException {

    public AssetNotFoundException(String ticker) {
        super("Asset with ticker '" + ticker + "' does not exist.");
    }
}

