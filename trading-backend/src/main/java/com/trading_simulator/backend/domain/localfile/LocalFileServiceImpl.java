package com.trading_simulator.backend.domain.localfile;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocalFileServiceImpl implements LocalFileService {
    private final LocalFileRepository localFileRepository;

    @Override
    public List<LocalFile> find() {
        return List.of();
    }

    @Override
    public LocalFile save(LocalFile file) {
        return null;
    }

    @Override
    public Boolean deleteById(String id) {
        return null;
    }
}
