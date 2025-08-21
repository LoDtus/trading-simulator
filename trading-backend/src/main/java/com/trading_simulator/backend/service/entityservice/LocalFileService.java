package com.trading_simulator.backend.service.entityservice;

import com.trading_simulator.backend.object.entity.LocalFile;
import org.springframework.cglib.core.Local;

import java.util.List;

public interface LocalFileService {
    List<LocalFile> find();
    LocalFile save(LocalFile file);
    Boolean deleteById(String id);
}
