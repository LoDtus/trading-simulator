package com.trading_simulator.backend.service.entityservice;

import com.trading_simulator.backend.object.entity.Auth;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthRepository authRepository;

    @Override
    public List<Auth> find() {
        return List.of();
    }

    @Override
    public Auth findById(String id) {
        return authRepository.findById(id).orElse(null);
    }

    @Override
    public Auth findByEmail(String email) {
        return authRepository.findByEmail(email).orElse(null);
    }

    @Override
    public Auth findByUsername(String username) {
        return authRepository.findByUsername(username).orElse(null);
    }

    @Override
    public Boolean existsByEmailOrUsername(String emailOrUsername) {
        Boolean existsByEmail = authRepository.existsByEmail(emailOrUsername);
        Boolean existsByUsername = authRepository.existsByUsername(emailOrUsername);
        return existsByEmail || existsByUsername;
    }

    @Override
    public Auth save(Auth auth) {
        return authRepository.save(auth);
    }

    @Override
    public Boolean deleteById(String id) {
        if (!authRepository.existsById(id)) return false;
        authRepository.deleteById(id);
        return true;
    }
}
