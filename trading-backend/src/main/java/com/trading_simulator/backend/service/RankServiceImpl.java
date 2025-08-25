package com.trading_simulator.backend.service;

import com.trading_simulator.backend.object.entity.RankRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RankServiceImpl implements RankService {
    private final RankRepository rankRepository;
}
