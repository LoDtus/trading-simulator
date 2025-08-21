package com.trading_simulator.backend.service.entityservice;

import com.trading_simulator.backend.object.entity.ApiPermission;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ApiPermissionRepository extends MongoRepository<ApiPermission, String> {
}
