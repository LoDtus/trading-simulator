package com.trading_simulator.backend.domain.role;

import com.trading_simulator.backend.config.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public List<Role> getRoles() {
        return List.of();
    }

    @Override
    public Role addRole() {
        return null;
    }

    @Override
    public Role updateRole() {
        return null;
    }

    @Override
    public void deleteRole() {

    }
}
