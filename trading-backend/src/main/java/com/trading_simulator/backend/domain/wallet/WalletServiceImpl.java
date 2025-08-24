package com.trading_simulator.backend.domain.wallet;

import com.trading_simulator.backend.config.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {
    private final WalletRepository walletRepository;

    @Override
    public void deleteById(String id) {
        if (!walletRepository.existsById(id)) {
            throw new NotFoundException("Wallet not found: " + id);
        }
        walletRepository.deleteById(id);
    }
}
