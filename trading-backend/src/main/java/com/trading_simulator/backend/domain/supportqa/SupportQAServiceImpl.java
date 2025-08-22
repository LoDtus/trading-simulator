package com.trading_simulator.backend.domain.supportqa;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SupportQAServiceImpl implements SupportQAService {
    private final SupportQARepository supportQARepository;

    @Override
    public SupportQA save(SupportQA supportQA) {
        return supportQARepository.save(supportQA);
    }

    @Override
    public Boolean deleteById(String id) {
        return null;
    }
}
