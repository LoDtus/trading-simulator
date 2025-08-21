package com.trading_simulator.backend.domain.rank;

import java.util.List;

public interface RankService {
    List<Rank> find();
    Rank save(Rank rank);
    Boolean deleteById(String id);
}
