package com.trading_simulator.backend.domain.supportqa;

public interface SupportQAService {
    SupportQA save(SupportQA supportQA);
    Boolean deleteById(String id);
}
