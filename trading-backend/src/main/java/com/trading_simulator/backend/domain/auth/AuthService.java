package com.trading_simulator.backend.domain.auth;

import java.util.List;

public interface AuthService {
    List<Auth> find();
    Auth findById(String id);
    Auth findByEmail(String email);
    Auth findByUsername(String username);
    Boolean existsByEmailOrUsername(String emailOrUsername);
    Auth save(Auth auth);
    Boolean deleteById(String id);
}
