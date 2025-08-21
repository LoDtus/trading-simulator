package com.trading_simulator.backend.service.entityservice;

import com.trading_simulator.backend.object.entity.ApiPermission;

import java.util.List;

public interface ApiPermissionService {
    List<ApiPermission> findAll();
    ApiPermission findById(String id);
    ApiPermission save(ApiPermission apiPermission);
    Boolean deleteById(String id);
}
