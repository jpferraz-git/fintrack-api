package com.backend.project.interfaces.controllers;

import com.backend.project.application.service.BinanceService;
import com.backend.project.interfaces.dto.binance.klines.BinanceKlinesResponseDTO;
import com.backend.project.interfaces.dto.binance.price.BinancePriceResponseDTO;
import com.backend.project.interfaces.dto.binance.ticker.Binance24hTickerResponseDTO;
import com.backend.project.interfaces.swagger.BinanceControllerSwagger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/binance")
public class BinanceController implements BinanceControllerSwagger {

    private final BinanceService binanceService;

    public BinanceController(BinanceService binanceService) {
        this.binanceService = binanceService;
    }

    @GetMapping("/price")
    public ResponseEntity<List<BinancePriceResponseDTO>> getPriceScheduled() {
        return ResponseEntity.ok().body(binanceService.scheduledPriceUpdate());
    }


    @GetMapping("/price")
    public ResponseEntity<BinancePriceResponseDTO> getPrice(@RequestParam String symbol) {
        return ResponseEntity.ok().body(binanceService.getPrice(symbol));
    }

    @GetMapping("/24h")
    public ResponseEntity<List<Binance24hTickerResponseDTO>> get24PriceScheduled(){
        return ResponseEntity.ok().body(binanceService.scheduled24hTickerUpdate());
    }


    @GetMapping("/24h")
    public ResponseEntity<Binance24hTickerResponseDTO> get24Price(@RequestParam String symbol){
        return ResponseEntity.ok().body(binanceService.get24hPrice(symbol));
    }

    @GetMapping("/klines")
    public ResponseEntity<List<BinanceKlinesResponseDTO>> getKlinesScheduled() {
        return ResponseEntity.ok().body(binanceService.scheduledKlinesUpdate());
    }

    @GetMapping("/klines")
    public ResponseEntity<BinanceKlinesResponseDTO> getKlines(@RequestParam String symbol, @RequestParam String interval) {
        return ResponseEntity.ok().body(binanceService.getKlines(symbol, interval));
    }
}
