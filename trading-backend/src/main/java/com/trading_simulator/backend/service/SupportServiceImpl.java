package com.trading_simulator.backend.service;

import com.trading_simulator.backend.object.entity.SupportQARepository;
import com.trading_simulator.backend.object.entity.SupportTopicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SupportServiceImpl implements SupportService {
    private final SupportQARepository supportQARepository;
    private final SupportTopicRepository supportTopicRepository;
}
