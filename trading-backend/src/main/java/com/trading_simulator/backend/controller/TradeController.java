package com.trading_simulator.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/trade")
@RequiredArgsConstructor
public class TradeController {
    // Xử lý “giao dịch đã khớp” (là kết quả của quá trình order)
}
