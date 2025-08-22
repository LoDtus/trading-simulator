package com.trading_simulator.backend.controller.test;

import com.trading_simulator.backend.externalservice.BinanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test/binance")
public class BinanceController {
    private final BinanceService binanceService;

    @GetMapping("/spot/recent-trades")
    public ResponseEntity<String> getRecentTrades(@RequestParam String symbol, @RequestParam int limit) {
        return ResponseEntity.ok(binanceService.getRecentTrades(symbol, limit));
    }
}