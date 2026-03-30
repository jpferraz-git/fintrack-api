package com.backend.project.application.service;

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

@Service
public class BinanceService {

    private final BinanceIntegration binanceIntegration;
    private final Binance24hTickerMapper binance24hTickerMapper;
    private final BinanceKlinesMapper binanceKlinesMapper;

    public BinanceService(BinanceIntegration binanceIntegration, Binance24hTickerMapper binance24hTickerMapper, BinanceKlinesMapper binanceKlinesMapper) {
        this.binanceIntegration = binanceIntegration;
        this.binance24hTickerMapper = binance24hTickerMapper;
        this.binanceKlinesMapper = binanceKlinesMapper;
    }

    private static final List<String> TRACKED_SYMBOLS =
            List.of("BTCUSDT", "ETHUSDT", "BNBUSDT", "SOLUSDT", "XRPUSDT");

    @Scheduled(fixedRate = 5000)
    public List<BinancePriceResponseDTO> scheduledPriceUpdate() {
        System.out.println(TRACKED_SYMBOLS.stream().map(this::getPrice).toList());
       return TRACKED_SYMBOLS.stream().map(this::getPrice).toList();
    }

    public BinancePriceResponseDTO getPrice(String symbol) {
        return binanceIntegration.getPrice(symbol);
    }

    @Scheduled(fixedDelay = 10000)
    public List<Binance24hTickerResponseDTO> scheduled24hTickerUpdate() {
        return TRACKED_SYMBOLS.stream().map(this::get24hPrice).toList();
    }

    public Binance24hTickerResponseDTO get24hPrice(String symbol){
        return binance24hTickerMapper.toBinance24hTickerResponseDTO(binanceIntegration.get24hTicker(symbol));
    }

    @Scheduled(fixedDelay = 10000)
    public List<BinanceKlinesResponseDTO> scheduledKlinesUpdate() {
        return TRACKED_SYMBOLS.stream()
                .map(symbol -> getKlines(symbol, "1m"))
                .toList();
    }

    public BinanceKlinesResponseDTO getKlines(String symbol, String interval){
        List<BinanceKlinesRequestDTO> dtos = binanceIntegration.getKlines(symbol, interval);
        return binanceKlinesMapper.toResponseKlines(dtos.getLast());
    }
}
