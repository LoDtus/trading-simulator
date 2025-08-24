package com.trading_simulator.backend.common.util;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;
import java.util.regex.Pattern;

@NoArgsConstructor
public class CommonUtil {

    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
    public Boolean isValidEmail(String email) {
        if (email == null) return false;
        return EMAIL_PATTERN.matcher(email).matches();
    }

    private static final String PHONE_REGEX = "^(\\+84|0)\\d{9}$";
    private static final Pattern PHONE_PATTERN = Pattern.compile(PHONE_REGEX);
    public Boolean isValidPhoneNumber(String phoneNumber) {
        if (phoneNumber == null) return false;
        return PHONE_PATTERN.matcher(phoneNumber).matches();
    }

    public static <T, ID> String generateUniqueUUID(MongoRepository<T, String> repository) {
        String uuid;
        do {
            uuid = UUID.randomUUID().toString();
        } while (repository.existsById(uuid));
        return uuid;
    }
}
