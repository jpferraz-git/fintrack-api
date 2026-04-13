package com.backend.project.exception;

import java.util.UUID;

public class AssetNotFoundException extends RuntimeException {

    public AssetNotFoundException(String symbol) {
        super("Asset with symbol '" + symbol + "' does not exist.");
    }

    public AssetNotFoundException(UUID assetId) {
        super("Asset with id '" + assetId + "' does not exist.");
    }
}

