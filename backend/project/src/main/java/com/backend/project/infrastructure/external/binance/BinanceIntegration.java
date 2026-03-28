package com.backend.project.infrastructure.external.binance;

import com.backend.project.interfaces.dto.binance.klines.BinanceKlinesRequestDTO;
import com.backend.project.interfaces.dto.binance.price.BinancePriceResponseDTO;
import com.backend.project.interfaces.dto.binance.ticker.Binance24hTickerRequestDTO;
import org.springframework.stereotype.Component;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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

    public List<BinanceKlinesRequestDTO> getKlines(String symbol, String interval){
       List<List<Object>> rawKlines = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/klines")
                        .queryParam("symbol", symbol)
                        .queryParam("interval", interval)
                        .build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<List<Object>>>() {})
                .block();

       if (rawKlines == null || rawKlines.isEmpty()) {
           return List.of();
       }

       List<BinanceKlinesRequestDTO> klines = new ArrayList<>();
       for (List<Object> row : rawKlines) {
           if (row == null || row.size() < 11) {
               continue;
           }

           klines.add(new BinanceKlinesRequestDTO(
                   toLong(row.get(0)),
                   toBigDecimal(row.get(1)),
                   toBigDecimal(row.get(2)),
                   toBigDecimal(row.get(3)),
                   toBigDecimal(row.get(4)),
                   toBigDecimal(row.get(5)),
                   toLong(row.get(6)),
                   toBigDecimal(row.get(7)),
                   toInteger(row.get(8)),
                   toBigDecimal(row.get(9)),
                   toBigDecimal(row.get(10))
           ));
       }

       return klines;
    }

    private Long toLong(Object value) {
        return value == null ? null : Long.valueOf(value.toString());
    }

    private Integer toInteger(Object value) {
        return value == null ? null : Integer.valueOf(value.toString());
    }

    private BigDecimal toBigDecimal(Object value) {
        return value == null ? null : new BigDecimal(value.toString());
    }

}
