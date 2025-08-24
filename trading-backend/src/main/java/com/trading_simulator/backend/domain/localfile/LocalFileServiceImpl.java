package com.trading_simulator.backend.domain.localfile;

import com.trading_simulator.backend.config.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocalFileServiceImpl implements LocalFileService {
    private final LocalFileRepository localFileRepository;
}
