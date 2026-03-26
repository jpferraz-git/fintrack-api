package com.backend.project.interfaces.controllers;

import com.backend.project.application.service.BinanceService;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/binance")
public class BinanceController {

    private final BinanceService binanceService;

    public BinanceController(BinanceService binanceService) {
        this.binanceService = binanceService;
    }

    @GetMapping("/{symbol}")
    public String getPrice(@PathVariable String symbol) {
        return binanceService.getPrice(symbol);
    }

}
