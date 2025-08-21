package com.trading_simulator.backend.domain.apipermission;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ApiPermissionRepository extends MongoRepository<ApiPermission, String> {
}
