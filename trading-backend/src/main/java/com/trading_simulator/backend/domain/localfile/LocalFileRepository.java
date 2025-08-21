package com.trading_simulator.backend.domain.localfile;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface LocalFileRepository extends MongoRepository<LocalFile, String> {
}
