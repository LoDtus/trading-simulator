package com.trading_simulator.backend.service;

import com.trading_simulator.backend.object.entity.TradeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TradeServiceImpl implements TradeService {
    private final TradeRepository tradeRepository;
}
