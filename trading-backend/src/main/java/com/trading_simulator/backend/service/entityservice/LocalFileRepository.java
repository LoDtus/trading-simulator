package com.trading_simulator.backend.service.entityservice;

import com.trading_simulator.backend.object.entity.LocalFile;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LocalFileRepository extends MongoRepository<LocalFile, String> {
}
