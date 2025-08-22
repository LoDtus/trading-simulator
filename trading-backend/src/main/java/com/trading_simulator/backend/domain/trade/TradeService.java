package com.trading_simulator.backend.domain.trade;

public interface TradeService {
    Trade save(Trade trade);
    Boolean deleteById(String id);
}
