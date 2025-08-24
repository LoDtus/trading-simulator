package com.trading_simulator.backend.domain.user;

import com.trading_simulator.backend.config.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public void getUsers() {

    }

    @Override
    public void updateUser() {

    }

    @Override
    public void deleteUsers(List<String> ids) {

    }
}
