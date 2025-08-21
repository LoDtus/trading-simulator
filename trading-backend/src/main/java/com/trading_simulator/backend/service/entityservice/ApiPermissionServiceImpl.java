package com.trading_simulator.backend.service.entityservice;

import com.trading_simulator.backend.object.entity.ApiPermission;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApiPermissionServiceImpl implements ApiPermissionService {
    private final ApiPermissionRepository apiPermissionRepository;

    @Override
    public List<ApiPermission> findAll() {
        return apiPermissionRepository.findAll();
    }

    @Override
    public ApiPermission findById(String id) {
        return apiPermissionRepository.findById(id).orElse(null);
    }

    @Override
    public ApiPermission save(ApiPermission apiPermission) {
        return apiPermissionRepository.save(apiPermission);
    }

    @Override
    public Boolean deleteById(String id) {
        if (!apiPermissionRepository.existsById(id)) return false;
        apiPermissionRepository.deleteById(id);
        return true;
    }
}
