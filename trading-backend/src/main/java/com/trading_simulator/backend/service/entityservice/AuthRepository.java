package com.trading_simulator.backend.service.entityservice;

import com.trading_simulator.backend.object.entity.Auth;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface AuthRepository extends MongoRepository<Auth, String> {
    // Tìm theo email
    Optional<Auth> findByEmail(String email);

    // Tìm theo username
    Optional<Auth> findByUsername(String username);

    // Kiểm tra tồn tại
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
}
