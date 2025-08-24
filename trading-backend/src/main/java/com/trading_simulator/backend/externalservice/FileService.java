package com.trading_simulator.backend.externalservice;

import com.trading_simulator.backend.domain.localfile.LocalFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileService {
    LocalFile uploadFile(
            MultipartFile file,
            String storagePath,
            String baseUrl
    ) throws IOException;
    void deleteFile(String id);
}