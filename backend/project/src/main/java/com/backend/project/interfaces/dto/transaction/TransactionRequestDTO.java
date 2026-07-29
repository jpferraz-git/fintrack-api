package com.backend.project.interfaces.dto.transaction;

import java.math.BigDecimal;
import java.util.UUID;

public record TransactionRequestDTO (
        @jakarta.validation.constraints.NotNull
        UUID fkUser,
        @jakarta.validation.constraints.NotBlank
        String symbol,
        @jakarta.validation.constraints.NotBlank
        String type,
        @jakarta.validation.constraints.NotNull
        @jakarta.validation.constraints.DecimalMin("0.0")
        BigDecimal quantity,
        @jakarta.validation.constraints.NotNull
        @jakarta.validation.constraints.DecimalMin("0.0")
        BigDecimal price
){}
