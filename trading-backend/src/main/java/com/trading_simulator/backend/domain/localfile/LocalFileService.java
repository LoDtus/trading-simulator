package com.trading_simulator.backend.domain.localfile;

import java.util.List;

public interface LocalFileService {
    List<LocalFile> find();
    LocalFile save(LocalFile file);
    Boolean deleteById(String id);
}
