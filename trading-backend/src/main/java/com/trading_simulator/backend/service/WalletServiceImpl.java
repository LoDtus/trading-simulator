package com.trading_simulator.backend.service;

import com.trading_simulator.backend.config.exception.NotFoundException;
import com.trading_simulator.backend.object.entity.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {
    private final WalletRepository walletRepository;

    @Override
    public void deleteById(String id) {
        if (!walletRepository.existsById(id)) {
            throw new NotFoundException("Wallet not found");
        }
        walletRepository.deleteById(id);
    }
}
