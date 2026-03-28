package com.backend.project.interfaces.swagger;

import com.backend.project.interfaces.dto.binance.klines.BinanceKlinesResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Binance Klines", description = "Operations for retrieving Binance candlestick data")
public interface BinanceKlinesControllerSwagger {

	@Operation(summary = "Get klines", description = "Retrieves the first kline record for a symbol and interval")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Kline retrieved successfully",
					content = @Content(schema = @Schema(implementation = BinanceKlinesResponseDTO.class))),
			@ApiResponse(responseCode = "404", description = "No klines found for the provided symbol and interval", content = @Content),
			@ApiResponse(responseCode = "400", description = "Invalid request parameters", content = @Content)
	})
	ResponseEntity<BinanceKlinesResponseDTO> getKlines(
			@Parameter(description = "Trading pair symbol", example = "BTCUSDT", required = true)
			String symbol,
			@Parameter(description = "Candlestick interval", example = "1h", required = true)
			String interval);
}

