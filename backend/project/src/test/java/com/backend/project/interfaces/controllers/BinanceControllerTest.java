package com.backend.project.interfaces.controllers;

import com.backend.project.application.Result;
import com.backend.project.application.service.BinanceService;
import com.backend.project.exception.GlobalExceptionHandler;
import com.backend.project.interfaces.dto.binance.klines.BinanceKlinesResponseDTO;
import com.backend.project.interfaces.dto.binance.price.BinancePriceResponseDTO;
import com.backend.project.interfaces.dto.binance.ticker.Binance24hTickerResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class BinanceControllerTest {

    private MockMvc mockMvc;

    @Mock
    private BinanceService binanceService;

    @BeforeEach
    void setup() {
        BinanceController binanceController = new BinanceController(binanceService);
        mockMvc = MockMvcBuilders.standaloneSetup(binanceController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void getPriceShouldReturnDataForSymbol() throws Exception {
        BinancePriceResponseDTO response = new BinancePriceResponseDTO(
                1L,
                "BTCUSDT",
                new BigDecimal("65000.10"),
                Instant.now(),
                Instant.now()
        );

        when(binanceService.getPrice("BTCUSDT")).thenReturn(Result.ok(response));

        mockMvc.perform(get("/binance/price").param("symbol", "BTCUSDT"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.symbol").value("BTCUSDT"))
                .andExpect(jsonPath("$.price").value(65000.10));
    }

    @Test
    void get24hShouldReturnDataForSymbol() throws Exception {
        Binance24hTickerResponseDTO response = new Binance24hTickerResponseDTO(
                "BTCUSDT",
                new BigDecimal("1000.00"),
                new BigDecimal("1.20"),
                new BigDecimal("64000.00"),
                new BigDecimal("64010.00"),
                new BigDecimal("65000.00"),
                new BigDecimal("0.1000"),
                new BigDecimal("64999.00"),
                new BigDecimal("63000.00"),
                new BigDecimal("900.00"),
                new BigDecimal("58000000.00"),
                Instant.now(),
                Instant.now(),
                1L,
                2L,
                3L
        );

        when(binanceService.get24hPrice("BTCUSDT")).thenReturn(Result.ok(response));

        mockMvc.perform(get("/binance/24h").param("symbol", "BTCUSDT"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.symbol").value("BTCUSDT"))
                .andExpect(jsonPath("$.lastPrice").value(65000.00));
    }

    @Test
    void getKlinesShouldReturnDataForSymbolAndInterval() throws Exception {
        BinanceKlinesResponseDTO response = new BinanceKlinesResponseDTO(
                Instant.now(),
                new BigDecimal("64000.00"),
                new BigDecimal("65500.00"),
                new BigDecimal("63800.00"),
                new BigDecimal("65000.00"),
                new BigDecimal("120.00"),
                Instant.now(),
                new BigDecimal("7800000.00"),
                200,
                new BigDecimal("60.00"),
                new BigDecimal("3900000.00")
        );

        when(binanceService.getKlines("BTCUSDT", "1m", 80)).thenReturn(Result.ok(List.of(response)));

        mockMvc.perform(get("/binance/klines")
                        .param("symbol", "BTCUSDT")
                        .param("interval", "1m"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].open").value(64000.00))
                .andExpect(jsonPath("$[0].close").value(65000.00));
    }

    @Test
    void getScheduledPricesShouldReturnDefaultTrackedList() throws Exception {
        BinancePriceResponseDTO btc = new BinancePriceResponseDTO(
                1L,
                "BTCUSDT",
                new BigDecimal("65000.10"),
                Instant.now(),
                Instant.now()
        );

        when(binanceService.scheduledPriceUpdate()).thenReturn(Result.ok(List.of(btc)));

        mockMvc.perform(get("/binance/price"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].symbol").value("BTCUSDT"));
    }

    @Test
    void getPriceShouldReturnNotFoundWhenPriceDoesNotExist() throws Exception {
        when(binanceService.getPrice("BTCUSDT")).thenReturn(Result.fail("Price not found"));

        mockMvc.perform(get("/binance/price").param("symbol", "BTCUSDT"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Price not found"));
    }
}


