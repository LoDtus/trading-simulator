package com.trading_simulator.backend.service.entityservice;

import com.trading_simulator.backend.object.entity.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoleRepository extends MongoRepository<Role, String> {
}
