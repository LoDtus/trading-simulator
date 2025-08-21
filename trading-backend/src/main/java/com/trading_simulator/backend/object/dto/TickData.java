package com.trading_simulator.backend.object.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class TickData {
    private String symbol;      // Cặp tiền tệ, ví dụ: "EUR/USD"
    private long timestamp;     // Thời gian tick (epoch millis hoặc ISO-8601)
    private double bid;         // Giá mua (bid price)
    private double ask;         // Giá bán (ask price)
    private double mid;         // Giá giữa (tùy chọn, = (bid + ask) / 2)
}
