package com.backend.project.exception;

import java.util.UUID;

public class AssetNotFoundException extends RuntimeException {

    public AssetNotFoundException(String ticker) {
        super("Asset with ticker '" + ticker + "' does not exist.");
    }

    public AssetNotFoundException(UUID assetId) {
        super("Asset with id '" + assetId + "' does not exist.");
    }
}

