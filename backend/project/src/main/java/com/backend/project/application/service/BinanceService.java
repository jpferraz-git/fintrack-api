package com.backend.project.application.service;

import com.backend.project.domain.utils.Result;
import com.backend.project.infrastructure.binance.BinanceIntegration;
import com.backend.project.interfaces.dto.binance.klines.BinanceKlinesMapper;
import com.backend.project.interfaces.dto.binance.klines.BinanceKlinesRequestDTO;
import com.backend.project.interfaces.dto.binance.klines.BinanceKlinesResponseDTO;
import com.backend.project.interfaces.dto.binance.price.BinancePriceResponseDTO;
import com.backend.project.interfaces.dto.binance.ticker.Binance24hTickerMapper;
import com.backend.project.interfaces.dto.binance.ticker.Binance24hTickerResponseDTO;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BinanceService {

    private final BinanceCacheService binanceCacheService;
    private final BinanceIntegration binanceIntegration; // kept for klines
    private final Binance24hTickerMapper binance24hTickerMapper;
    private final BinanceKlinesMapper binanceKlinesMapper;

    public BinanceService(BinanceCacheService binanceCacheService, BinanceIntegration binanceIntegration, Binance24hTickerMapper binance24hTickerMapper, BinanceKlinesMapper binanceKlinesMapper) {
        this.binanceCacheService = binanceCacheService;
        this.binanceIntegration = binanceIntegration;
        this.binance24hTickerMapper = binance24hTickerMapper;
        this.binanceKlinesMapper = binanceKlinesMapper;
    }

    private static final List<String> TRACKED_SYMBOLS =
            List.of("BTCUSDT", "ETHUSDT", "BNBUSDT", "SOLUSDT", "XRPUSDT");

    @Scheduled(fixedRate = 5000)
    public Result<List<BinancePriceResponseDTO>> scheduledPriceUpdate() {
        Optional<List<BinancePriceResponseDTO>> prices = Optional.of(
                TRACKED_SYMBOLS.stream()
                        .map(this::getPrice)
                        .filter(Result::isOk)
                        .map(Result::getValue)
                        .toList()
        );
        return prices.map(Result::ok).orElseGet(() -> Result.fail("Failed to retrieve prices"));
    }

    public Result<BinancePriceResponseDTO> getPrice(String symbol) {
        Optional<BinancePriceResponseDTO> price = Optional.ofNullable(binanceCacheService.getPrice(symbol));
        return price.map(Result::ok).orElseGet(() -> Result.fail("Price not found"));
    }

    @Scheduled(fixedDelay = 10000)
    public Result<List<Binance24hTickerResponseDTO>> scheduled24hTickerUpdate() {
        List<Binance24hTickerResponseDTO> tickers = TRACKED_SYMBOLS.stream()
                .map(this::get24hPrice)
                .filter(Result::isOk)
                .map(Result::getValue)
                .toList();

        if (tickers.isEmpty()) {
            return Result.fail("Failed to retrieve 24h ticker data");
        }

        return Result.ok(tickers);
    }

    public Result<Binance24hTickerResponseDTO> get24hPrice(String symbol){
        try {
            var ticker = binanceCacheService.get24hTicker(symbol);
            if (ticker == null) {
                return Result.fail("24h ticker not found");
            }
            return Result.ok(binance24hTickerMapper.toBinance24hTickerResponseDTO(ticker));
        } catch (Exception ex) {
            return Result.fail(ex.getMessage());
        }
    }

    @Scheduled(fixedDelay = 2000)
    public Result<List<BinanceKlinesResponseDTO>> scheduledKlinesUpdate() {
        List<BinanceKlinesResponseDTO> klines = TRACKED_SYMBOLS.stream()
                .map(symbol -> getKlines(symbol, "1m", 80))
                .filter(Result::isOk)
                .map(Result::getValue)
                .flatMap(List::stream)
                .toList();;
        if (klines.isEmpty()) {
            return Result.fail("Failed to retrieve klines");
        }

        return Result.ok(klines);
    }

    public Result<List<BinanceKlinesResponseDTO>> getKlines(String symbol, String interval, Integer limit){
        try {
            List<BinanceKlinesRequestDTO> dtos = binanceIntegration.getKlines(symbol, interval, limit);
            if (dtos == null || dtos.isEmpty()) {
                return Result.fail("No klines found for symbol and interval");
            }
            return Result.ok(binanceKlinesMapper.toResponseKlines(dtos));
        } catch (Exception ex) {
            return Result.fail(ex.getMessage());
        }
    }
}
