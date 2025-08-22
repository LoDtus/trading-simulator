package com.trading_simulator.backend.domain.wallet;

public interface WalletService {
    Wallet save(Wallet wallet);
    Boolean deleteById(String id);
}
