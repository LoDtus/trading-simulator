package com.trading_simulator.backend.domain.apipermission;

import java.util.List;

public interface ApiPermissionService {
    List<ApiPermission> findAll();
    ApiPermission findById(String id);
    ApiPermission save(ApiPermission apiPermission);
    Boolean deleteById(String id);
}
