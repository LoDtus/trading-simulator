package com.trading_simulator.backend.service.externalservice;

import com.trading_simulator.backend.object.dto.TickData;

import java.net.URL;
import java.time.YearMonth;
import java.util.List;

// Bao gồm các dữ liệu ngoại hối như USD, VND...
public interface TrueFXService {
    // Session Management
    String connect(String currencyPairs, String format);  // trả về session ID
    void disconnect(String sessionId);
    String reconnect(String sessionId);

    // Realtime Data (public, snapshot or incremental)
    String query(String sessionId);

    // Parser
    List<TickData> parse(String rawResponse);

    // Historical Data Download URL builder
    URL getHistoricalDataUrl(String symbol, YearMonth date);
}