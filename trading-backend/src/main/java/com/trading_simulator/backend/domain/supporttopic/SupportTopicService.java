package com.trading_simulator.backend.domain.supporttopic;

public interface SupportTopicService {
    SupportTopic save(SupportTopic supportTopic);
    Boolean deleteById(String id);
}
