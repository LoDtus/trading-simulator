package com.trading_simulator.backend.domain.user;

public interface UserService {
    User findById(String id);
    User save(User user);
    Boolean deleteById(String id);
}
