package com.trading_simulator.backend.service.entityservice;

import com.trading_simulator.backend.object.entity.Profile;

public interface ProfileService {
    Profile findById(String id);
    Profile save(Profile profile);
    Boolean deleteById(String id);
}
