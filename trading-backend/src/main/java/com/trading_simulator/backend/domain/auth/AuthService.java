package com.trading_simulator.backend.domain.auth;

import java.util.List;

public interface AuthService {
    List<Auth> find();
    Auth findById(String id);
    Auth findByEmail(String email);
    Auth findByUsername(String username);
    Auth findByEmailOrUsername(String emailOrUsername);
    Boolean existsByEmail(String email);
    Boolean existsByUsername(String username);
    Auth save(Auth auth);
    Boolean deleteById(String id);
}
