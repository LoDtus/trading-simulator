package com.trading_simulator.backend.object.entity;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AuthRepository extends MongoRepository<Auth, String> {
    Optional<Auth> findByEmail(String email);
    Optional<Auth> findByUsername(String username);
    List<Auth> findByRoleId(String roleId);

    boolean existsByEmail(String email);
    boolean existsByUsername(String username);

    @Query("{ '$or': [ {'email': ?0'}, {'username': ?0'} ] }")
    Optional<Auth> findByEmailOrUsername(String input);
}
