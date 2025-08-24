package com.trading_simulator.backend.domain.supporttopic;

import com.trading_simulator.backend.config.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SupportTopicServiceImpl implements SupportTopicService {
    private final SupportTopicRepository supportTopicRepository;
}
