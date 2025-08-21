package com.trading_simulator.backend.domain.role;

import java.util.List;

public interface RoleService {
    List<Role> findAll();
    Role findById(String id);
    Role save(Role role);
    Boolean deleteById(String id);
}
