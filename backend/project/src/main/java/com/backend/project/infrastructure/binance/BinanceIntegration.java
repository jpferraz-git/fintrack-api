package com.backend.project.infrastructure.binance;

import com.backend.project.interfaces.dto.binance.klines.BinanceKlinesRequestDTO;
import com.backend.project.interfaces.dto.binance.price.BinancePriceResponseDTO;
import com.backend.project.interfaces.dto.binance.ticker.Binance24hTickerRequestDTO;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
public class BinanceIntegration {

    private final RestClient restClient;
    private final BinanceParser binanceParser;

    public BinanceIntegration(RestClient.Builder builder, BinanceParser binanceParser){
        this.restClient = builder
                .baseUrl("https://api.binance.com/api/v3")
                .build();
        this.binanceParser = binanceParser;
    }

    public BinancePriceResponseDTO getPrice(String symbol){
        return restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/ticker/price")
                        .queryParam("symbol", symbol)
                        .build())
                .retrieve()
                .body(BinancePriceResponseDTO.class);
    }
    public Binance24hTickerRequestDTO get24hTicker(String symbol){
        return restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/ticker/24hr")
                        .queryParam("symbol", symbol)
                        .build())
                .retrieve()
                .body(Binance24hTickerRequestDTO.class);
    }

    public List<BinanceKlinesRequestDTO> getKlines(String symbol, String interval, Integer limit){
        List<List<Object>> rawKlines = restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/klines")
                        .queryParam("symbol", symbol)
                        .queryParam("interval", interval)
                        .queryParam("limit", limit)
                        .build())
                .retrieve()
                .body(new ParameterizedTypeReference<List<List<Object>>>() {});
        return binanceParser.parseKlines(rawKlines);
    }

}
