package com.backend.project.interfaces.dto.transaction;

import java.math.BigDecimal;
import java.util.UUID;

public record TransactionRequestDTO (
        UUID fkUser,
        String symbol,
        String type,
        BigDecimal quantity,
        BigDecimal price
){}
