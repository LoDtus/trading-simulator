package com.trading_simulator.backend.domain.order;

public interface OrderService {
    Order save(Order order);
    Boolean deleteById(String id);
}
