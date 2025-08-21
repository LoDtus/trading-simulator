package com.trading_simulator.backend.domain.rank;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RankServiceImpl implements RankService {
    private final RankRepository rankRepository;

    @Override
    public List<Rank> find() {
        return List.of();
    }

    @Override
    public Rank save(Rank rank) {
        return rankRepository.save(rank);
    }

    @Override
    public Boolean deleteById(String id) {
        if (!rankRepository.existsById(id)) return false;
        rankRepository.deleteById(id);
        return true;
    }
}
