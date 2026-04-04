package com.backend.project.infrastructure.binance;

import com.backend.project.interfaces.dto.binance.klines.BinanceKlinesRequestDTO;
import com.backend.project.interfaces.dto.binance.price.BinancePriceResponseDTO;
import com.backend.project.interfaces.dto.binance.ticker.Binance24hTickerRequestDTO;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Component
public class BinanceIntegration {

    private final WebClient webClient;
    private final BinanceParser binanceParser;

    public BinanceIntegration(WebClient.Builder builder, BinanceParser binanceParser){
        this.webClient = builder
                .baseUrl("https://api.binance.com/api/v3")
                .build();
        this.binanceParser = binanceParser;
    }

    public BinancePriceResponseDTO getPrice(String symbol){
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/ticker/price")
                        .queryParam("symbol", symbol)
                        .build())
                .retrieve()
                .bodyToMono(BinancePriceResponseDTO.class)
                .block();
    }
    public Binance24hTickerRequestDTO get24hTicker(String symbol){
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/ticker/24hr")
                        .queryParam("symbol", symbol)
                        .build())
                .retrieve()
                .bodyToMono(Binance24hTickerRequestDTO.class)
                .block();
    }

    public List<BinanceKlinesRequestDTO> getKlines(String symbol, String interval, Integer limit){
        List<List<Object>> rawKlines = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/klines")
                        .queryParam("symbol", symbol)
                        .queryParam("interval", interval)
                        .queryParam("limit", limit)
                        .build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<List<Object>>>() {})
                .block();
        return binanceParser.parseKlines(rawKlines);
    }

}
