package com.trading_simulator.backend.service.entityservice;

import com.trading_simulator.backend.object.entity.Profile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {
    private final ProfileRepository profileRepository;

    @Override
    public Profile findById(String id) {
        return profileRepository.findById(id).orElse(null);
    }

    @Override
    public Profile save(Profile profile) {
        return profileRepository.save(profile);
    }

    @Override
    public Boolean deleteById(String id) {
        if (!profileRepository.existsById(id)) return false;
        profileRepository.deleteById(id);
        return true;
    }
}
