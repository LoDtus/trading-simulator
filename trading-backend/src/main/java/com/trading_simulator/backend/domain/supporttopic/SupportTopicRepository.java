package com.trading_simulator.backend.domain.supporttopic;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface SupportTopicRepository extends MongoRepository<SupportTopic, String> {
}