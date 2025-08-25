package com.trading_simulator.backend.service;

import com.trading_simulator.backend.object.entity.Role;

import java.util.List;

public interface RoleService {
    List<Role> getRoles();
    Role addRole();
    Role updateRole();
    void deleteRole();
}
