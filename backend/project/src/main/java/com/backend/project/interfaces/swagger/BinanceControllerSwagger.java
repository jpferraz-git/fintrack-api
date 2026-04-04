package com.backend.project.interfaces.swagger;

import com.backend.project.interfaces.dto.binance.klines.BinanceKlinesResponseDTO;
import com.backend.project.interfaces.dto.binance.price.BinancePriceResponseDTO;
import com.backend.project.interfaces.dto.binance.ticker.Binance24hTickerResponseDTO;
import com.backend.project.interfaces.dto.error.ErrorResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
@Tag(name = "Binance", description = "Operations for Binance market data")
public interface BinanceControllerSwagger {

    @Operation(summary = "Scheduled prices", description = "Returns the latest tracked prices for default symbols")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Prices retrieved successfully",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = BinancePriceResponseDTO.class))))
    })
    ResponseEntity<?> getPriceScheduled();

    @Operation(summary = "Price by symbol", description = "Returns the latest price for a specific symbol")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Price retrieved successfully",
                    content = @Content(schema = @Schema(implementation = BinancePriceResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters", content = @Content),
            @ApiResponse(responseCode = "404", description = "Binance resource not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "500", description = "Unexpected Binance response",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    ResponseEntity<?>  getPrice(
            @Parameter(description = "Trading pair symbol", example = "BTCUSDT", required = true)
            String symbol);

    @Operation(summary = "Scheduled 24h ticker", description = "Returns 24h ticker data for default tracked symbols")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "24h ticker data retrieved successfully",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Binance24hTickerResponseDTO.class))))
    })
    ResponseEntity<?> get24PriceScheduled();

    @Operation(summary = "24h ticker by symbol", description = "Returns 24h ticker data for a specific symbol")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "24h ticker retrieved successfully",
                    content = @Content(schema = @Schema(implementation = Binance24hTickerResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters", content = @Content),
            @ApiResponse(responseCode = "404", description = "Binance resource not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "500", description = "Unexpected Binance response",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    ResponseEntity<?> get24Price(
            @Parameter(description = "Trading pair symbol", example = "BTCUSDT", required = true)
            String symbol);

    @Operation(summary = "Scheduled klines", description = "Returns one latest kline record for each default tracked symbol")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Klines retrieved successfully",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = BinanceKlinesResponseDTO.class))))
    })
    ResponseEntity<?> getKlines(
            @Parameter(description = "Trading pair symbol", example = "BTCUSDT", required = true)
            String symbol,
            @Parameter(description = "Candlestick interval", example = "1m", required = true)
            String interval);
}

