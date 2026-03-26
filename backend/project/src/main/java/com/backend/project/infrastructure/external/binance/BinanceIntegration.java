package com.backend.project.infrastructure.external.binance;

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

    public String getPrice(String symbol){
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/ticker/price")
                        .queryParam("symbol", symbol)
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

}
