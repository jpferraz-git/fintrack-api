package com.backend.project.interfaces.controllers;

import com.backend.project.application.service.BinanceService;
import com.backend.project.interfaces.dto.binance.BinancePriceResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/binance")
public class BinanceController {

    private final BinanceService binanceService;

    public BinanceController(BinanceService binanceService) {
        this.binanceService = binanceService;
    }

    @GetMapping("/{symbol}")
    public ResponseEntity<BinancePriceResponseDTO> getPrice(@PathVariable String symbol) {
        return ResponseEntity.ok().body(binanceService.getPrice(symbol));
    }

    @GetMapping("/24h/{symbol}")
    public ResponseEntity<String> get24Price(@PathVariable String symbol){
        return ResponseEntity.ok().body(binanceService.get24hPrice(symbol));
    }

}
