package com.backend.project.infrastructure.external.binance;

import com.backend.project.interfaces.dto.binance.BinancePriceResponseDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class BinanceIntegration {

    private final WebClient webClient;

    public BinanceIntegration(WebClient.Builder builder){
        this.webClient = builder
                .baseUrl("https://api.binance.com/api/v3")
                .build();
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

    public String get24hTicker(String symbol){
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/ticker/24hr")
                        .queryParam("symbol", symbol)
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

}
