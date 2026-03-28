package com.backend.project.interfaces.controllers;

import com.backend.project.application.service.BinanceService;
import com.backend.project.interfaces.dto.binance.klines.BinanceKlinesResponseDTO;
import com.backend.project.interfaces.dto.binance.price.BinancePriceResponseDTO;
import com.backend.project.interfaces.dto.binance.ticker.Binance24hTickerResponseDTO;
import com.backend.project.interfaces.swagger.BinanceKlinesControllerSwagger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/binance")
public class BinanceController implements BinanceKlinesControllerSwagger {

    private final BinanceService binanceService;

    public BinanceController(BinanceService binanceService) {
        this.binanceService = binanceService;
    }

    @GetMapping("/{symbol}")
    public ResponseEntity<BinancePriceResponseDTO> getPrice(@PathVariable String symbol) {
        return ResponseEntity.ok().body(binanceService.getPrice(symbol));
    }

    @GetMapping("/24h/{symbol}")
    public ResponseEntity<Binance24hTickerResponseDTO> get24Price(@PathVariable String symbol){
        return ResponseEntity.ok().body(binanceService.get24hPrice(symbol));
    }

    @GetMapping("/klines")
    public ResponseEntity<BinanceKlinesResponseDTO> getKlines(@RequestParam String symbol, @RequestParam String interval) {
        return ResponseEntity.ok().body(binanceService.getKlines(symbol, interval));
    }

}
