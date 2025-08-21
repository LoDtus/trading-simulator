package com.trading_simulator.backend.service.entityservice;

import com.trading_simulator.backend.object.entity.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Role findById(String id) {
        return roleRepository.findById(id).orElse(null);
    }

    @Override
    public Role save(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Boolean deleteById(String id) {
        if (!roleRepository.existsById(id)) return false;
        roleRepository.deleteById(id);
        return true;
    }
}
