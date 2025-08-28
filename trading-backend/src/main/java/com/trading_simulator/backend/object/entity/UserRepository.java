package com.trading_simulator.backend.object.entity;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
    List<User> findByRoleId(String roleId);

    boolean existsByEmail(String email);
    boolean existsByUsername(String username);

    @Query("{ '$or': [ {'email': ?0'}, {'username': ?0'} ] }")
    Optional<User> findByEmailOrUsername(String input);
}
