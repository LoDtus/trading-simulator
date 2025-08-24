package com.trading_simulator.backend.domain.supportqa;

import com.trading_simulator.backend.config.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SupportQAServiceImpl implements SupportQAService {
    private final SupportQARepository supportQARepository;
}
