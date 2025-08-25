package com.trading_simulator.backend.service;

import com.trading_simulator.backend.config.exception.NotFoundException;
import com.trading_simulator.backend.object.entity.LocalFile;
import com.trading_simulator.backend.object.entity.LocalFileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {
    private final LocalFileRepository localFileRepository;

    @Value("${STORAGE_PATH}")
    private String STORAGE_PATH;

    @Override
    public LocalFile uploadFile(
            MultipartFile file,
            String storagePath,
            String baseUrl
    ) throws IOException {
        // Lấy ra phần mở rộng của file
        String originalFilename = file.getOriginalFilename();
        String extension = "";
        if (originalFilename != null) {
            int lastDot = originalFilename.lastIndexOf(".");
            if (lastDot > 0 && lastDot < originalFilename.length() - 1) {
                extension = originalFilename.substring(lastDot);
            }
        }

        // Lưu file vào thư mục, tao folder nếu chưa tồn tại
        String fileName = UUID.randomUUID().toString() + extension;
        Path filePath = Paths.get(storagePath, fileName);
        Files.createDirectories(filePath.getParent());
        Files.write(filePath, file.getBytes());

        String url = baseUrl + "/files/" + fileName;
        Long size = Files.size(filePath);

        return LocalFile.builder()
                .name(fileName)
                .url(url)
                .size(size)
                .build();
    }

    @Override
    public void deleteFile(String id) {
        LocalFile file = localFileRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Local file not found"));
        if (file == null) {
            throw new NotFoundException("Local file not found");
        };
//        String fileName = storageFile.getPreviewUrl().substring(storageFile.getPreviewUrl().lastIndexOf('/') + 1);

//        try {
//            // Tạo đường dẫn đầy đủ tới file
//            Path filePath = Paths.get(storagePath, fileName);
//
//            // Xóa file vật lý
//            Files.deleteIfExists(filePath); // Không ném lỗi nếu không tồn tại
//            storageFileRepository.deleteById(id);
//            return true;
//        } catch (IOException e) {
//            e.printStackTrace();
//            return false;
//        }
    }
}
