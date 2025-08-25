package com.trading_simulator.backend.service;

import com.trading_simulator.backend.object.entity.LocalFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {
    LocalFile uploadFile(
            MultipartFile file,
            String storagePath,
            String baseUrl
    ) throws IOException;
    void deleteFile(String id);
}