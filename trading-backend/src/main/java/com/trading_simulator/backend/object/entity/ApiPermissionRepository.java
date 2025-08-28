package com.trading_simulator.backend.object.entity;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ApiPermissionRepository extends MongoRepository<ApiPermission, String> {
    List<ApiPermission> findByEnabledTrue();
}
