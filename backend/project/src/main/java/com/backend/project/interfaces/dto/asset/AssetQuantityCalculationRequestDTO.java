package com.backend.project.interfaces.dto.asset;

import java.math.BigDecimal;

public record AssetQuantityCalculationRequestDTO(
        String symbol,
        BigDecimal investedValue
) {
}
