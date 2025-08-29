package com.trading_simulator.backend.common.util;

import jakarta.servlet.http.HttpServletResponse;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.io.IOException;
import java.util.UUID;
import java.util.regex.Pattern;

@NoArgsConstructor
public class CommonUtils {

    // Tìm và tạo UUID không trùng với bất kỳ UUID nào đã tồn tại trước đó
    public static <T, ID> String generateUniqueUUID(MongoRepository<T, String> repository) {
        String uuid;
        do {
            uuid = UUID.randomUUID().toString();
        } while (repository.existsById(uuid));
        return uuid;
    }

    // Gửi phản hồi lỗi
    public static void sendErrorResponse(
            HttpServletResponse response,
            Integer status,
            String message
    ) throws IOException {
        response.setStatus(status);
        response.getWriter().write(message);
        response.getWriter().flush();
    }

    // Kiểm tra định dạng Email
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
    public Boolean isValidEmail(String email) {
        if (email == null) return false;
        return EMAIL_PATTERN.matcher(email).matches();
    }
}
