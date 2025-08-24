package com.trading_simulator.backend.domain.user;

import java.util.List;

public interface UserService {
    void getUsers();
    void updateUser();
    void deleteUsers(List<String> ids);
}
