package com.backend.project.interfaces.dto.asset;

public record AssetRequestDTO (
        String ticker,
        String assetType,
        String companyName
){}
