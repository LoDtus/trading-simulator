package com.trading_simulator.backend.service.entityservice;

import com.trading_simulator.backend.object.entity.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProfileRepository extends MongoRepository<Profile, String> {
}
