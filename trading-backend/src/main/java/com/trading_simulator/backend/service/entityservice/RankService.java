package com.trading_simulator.backend.service.entityservice;

import com.trading_simulator.backend.object.entity.Rank;

import java.util.List;

public interface RankService {
    List<Rank> find();
    Rank save(Rank rank);
    Boolean deleteById(String id);
}
