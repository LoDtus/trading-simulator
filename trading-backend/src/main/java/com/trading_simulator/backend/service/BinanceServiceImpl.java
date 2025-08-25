package com.trading_simulator.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static com.trading_simulator.backend.common.Constant.BINANCE_FUTURES_URL;
import static com.trading_simulator.backend.common.Constant.BINANCE_SPOT_URL;

@Service
@RequiredArgsConstructor
public class BinanceServiceImpl implements BinanceService {
    private final RestTemplate restTemplate = new RestTemplate();

    // ================== SPOT API ==================
    @Override
    public String getCurrentPrice(String symbol) {
        return restTemplate.getForObject(BINANCE_SPOT_URL + "/api/v3/ticker/price?symbol=" + symbol, String.class);
    }

    @Override
    public String getKlines(String symbol, String interval, int limit) {
        return restTemplate.getForObject(BINANCE_SPOT_URL + "/api/v3/klines?symbol=" + symbol + "&interval=" + interval + "&limit=" + limit, String.class);
    }

    @Override
    public String getOrderBook(String symbol, int limit) {
        return restTemplate.getForObject(BINANCE_SPOT_URL + "/api/v3/depth?symbol=" + symbol + "&limit=" + limit, String.class);
    }

    @Override
    public String getRecentTrades(String symbol, int limit) {
        return restTemplate.getForObject(BINANCE_SPOT_URL + "/api/v3/trades?symbol=" + symbol + "&limit=" + limit, String.class);
    }

    @Override
    public String getAggTrades(String symbol, int limit) {
        return restTemplate.getForObject(BINANCE_SPOT_URL + "/api/v3/aggTrades?symbol=" + symbol + "&limit=" + limit, String.class);
    }

    @Override
    public String get24hrStats(String symbol) {
        return restTemplate.getForObject(BINANCE_SPOT_URL + "/api/v3/ticker/24hr?symbol=" + symbol, String.class);
    }

    @Override
    public String getAll24hrStats() {
        return restTemplate.getForObject(BINANCE_SPOT_URL + "/api/v3/ticker/24hr", String.class);
    }

    @Override
    public String getRollingWindowTicker(String symbol, String windowSize) {
        return restTemplate.getForObject(BINANCE_SPOT_URL + "/api/v3/ticker?symbol=" + symbol + "&windowSize=" + windowSize, String.class);
    }

    @Override
    public String getAveragePrice(String symbol) {
        return restTemplate.getForObject(BINANCE_SPOT_URL + "/api/v3/avgPrice?symbol=" + symbol, String.class);
    }

    @Override
    public String getExchangeInfo() {
        return restTemplate.getForObject(BINANCE_SPOT_URL + "/api/v3/exchangeInfo", String.class);
    }

    @Override
    public String getServerTime() {
        return restTemplate.getForObject(BINANCE_SPOT_URL + "/api/v3/time", String.class);
    }

    // ================== FUTURES API ==================
    @Override
    public String getMarkPrice(String symbol) {
        return restTemplate.getForObject(BINANCE_FUTURES_URL + "/fapi/v1/premiumIndex?symbol=" + symbol, String.class);
    }

    @Override
    public String getFundingRateHistory(String symbol, int limit) {
        return restTemplate.getForObject(BINANCE_FUTURES_URL + "/fapi/v1/fundingRate?symbol=" + symbol + "&limit=" + limit, String.class);
    }

    @Override
    public String getOpenInterest(String symbol) {
        return restTemplate.getForObject(BINANCE_FUTURES_URL + "/fapi/v1/openInterest?symbol=" + symbol, String.class);
    }

    @Override
    public String getTopLongShortAccountRatio(String symbol, String period, int limit) {
        String url = BINANCE_FUTURES_URL + "/futures/data/topLongShortAccountRatio?symbol=" + symbol
                + "&period=" + period
                + "&limit=" + limit;
        return restTemplate.getForObject(url, String.class);
    }
}