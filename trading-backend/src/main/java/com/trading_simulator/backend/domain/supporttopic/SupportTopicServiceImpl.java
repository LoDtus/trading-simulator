package com.trading_simulator.backend.domain.supporttopic;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SupportTopicServiceImpl implements SupportTopicService {
    private final SupportTopicRepository supportTopicRepository;

    @Override
    public SupportTopic save(SupportTopic supportTopic) {
        return supportTopicRepository.save(supportTopic);
    }

    @Override
    public Boolean deleteById(String id) {
        return null;
    }
}
