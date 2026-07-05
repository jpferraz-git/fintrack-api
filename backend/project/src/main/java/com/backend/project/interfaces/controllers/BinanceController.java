package com.backend.project.interfaces.controllers;

import com.backend.project.application.Result;
import com.backend.project.application.service.BinanceService;
import com.backend.project.interfaces.dto.binance.klines.BinanceKlinesResponseDTO;
import com.backend.project.interfaces.dto.binance.price.BinancePriceResponseDTO;
import com.backend.project.interfaces.dto.binance.ticker.Binance24hTickerResponseDTO;
import com.backend.project.interfaces.swagger.BinanceControllerSwagger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.backend.project.interfaces.controllers.utils.Normalizer.resolveStatus;


@RestController
@RequestMapping("/binance")
public class BinanceController implements BinanceControllerSwagger {

    private final BinanceService binanceService;

    public BinanceController(BinanceService binanceService) {
        this.binanceService = binanceService;
    }

    @GetMapping(value = "/price", params = "!symbol")
    public ResponseEntity<?> getPriceScheduled() {
        Result<List<BinancePriceResponseDTO>> result = binanceService.scheduledPriceUpdate();
        if (result.isOk()) {
            return ResponseEntity.ok(result.getValue());
        }
        return ResponseEntity.status(resolveStatus(result.getMessage())).body(result);
    }

    @GetMapping(value = "/price", params = "symbol")
    public ResponseEntity<?> getPrice(@RequestParam String symbol) {
        Result<BinancePriceResponseDTO> result = binanceService.getPrice(symbol);
        if (result.isOk()) {
            return ResponseEntity.ok(result.getValue());
        }
        return ResponseEntity.status(resolveStatus(result.getMessage())).body(result);
    }


    @GetMapping(value = "/24h", params = "!symbol")
    public ResponseEntity<?> get24PriceScheduled(){
        Result<List<Binance24hTickerResponseDTO>> result = binanceService.scheduled24hTickerUpdate();
        if (result.isOk()) {
            return ResponseEntity.ok(result.getValue());
        }
        return ResponseEntity.status(resolveStatus(result.getMessage())).body(result);
    }

    @GetMapping(value = "/24h", params = "symbol")
    public ResponseEntity<?> get24Price(@RequestParam String symbol){
        Result<Binance24hTickerResponseDTO> result = binanceService.get24hPrice(symbol);
        if (result.isOk()) {
            return ResponseEntity.ok(result.getValue());
        }
        return ResponseEntity.status(resolveStatus(result.getMessage())).body(result);
    }

    @GetMapping(value = "/klines", params = {"symbol", "interval"})
    public ResponseEntity<?> getKlines(@RequestParam String symbol, @RequestParam String interval, @RequestParam(defaultValue = "80") Integer limit) {
        Result<List<BinanceKlinesResponseDTO>> result = binanceService.getKlines(symbol, interval, limit);
        if (result.isOk()) {
            return ResponseEntity.ok(result.getValue());
        }
        return ResponseEntity.status(resolveStatus(result.getMessage())).body(result);
    }

}
