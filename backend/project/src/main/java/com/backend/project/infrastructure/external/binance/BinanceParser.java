package com.backend.project.infrastructure.external.binance;

import com.backend.project.interfaces.dto.binance.klines.BinanceKlinesRequestDTO;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class BinanceParser {

    public List<BinanceKlinesRequestDTO> parseKlines(List<List<Object>> rawKlines) {
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

