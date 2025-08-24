package com.trading_simulator.backend.domain.role;

import java.util.List;

public interface RoleService {
    List<Role> getRoles();
    Role addRole();
    Role updateRole();
    void deleteRole();
}
