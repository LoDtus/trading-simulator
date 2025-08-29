package com.trading_simulator.backend.config.validation;

import com.trading_simulator.backend.common.enums.InputType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;

import java.util.regex.Pattern;

public class InputValidator implements ConstraintValidator<ValidInput, String> {
    private InputType type;

    // USERNAME: chấp nhận chữ cái (hoa/thường), chữ số và các ký tự bao gồm: - _ .
    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[A-Za-z0-9_.\\-]+$");
    // PLAIN_TEXT: cho phép chữ latin, tượng hình, chữ số và các ký tự: . , ; : ' \ " ( ) \ \ - _ / & @ ! ?
    private static final Pattern PLAIN_TEXT_PATTERN = Pattern.compile("^[\\p{L}\\p{N}\\s.,;:'\"()\\-_/&@!?]*$");
    // COMMON_NAME (tên đặt chung cho file, folder...): cho phép dùng chữ latin, tượng hình, chữ số, emoji và các ký tự [] () - _ : , . |
    private static final Pattern COMMON_NAME_PATTERN = Pattern.compile("^[\\p{L}\\p{N}\\s\\p{So}\\[\\]\\(\\)\\-_:,\\.\\|]*$");
    // FULL_NAME (tên chính thức của người dùng): cho phép chữ latin, tượng hình, chữ số, emoji và các ký tự [] () - _ |
    private static final Pattern FULL_NAME_PATTERN = Pattern.compile("^[\\p{L}\\p{N}\\s\\p{So}\\[\\]\\(\\)\\-_\\|]*$");
    // LATIN_ALNUM: chỉ chấp nhận chữ latin và số
    private static final Pattern LATIN_ALNUM_PATTERN = Pattern.compile("^[A-Za-z0-9]*$");

    @Override
    public void initialize(ValidInput constraintAnnotation) {
        this.type = constraintAnnotation.type();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null || value.isBlank()) return true; // ngược lại với @NotBlank, cho phép blank hoặc null
        return switch (type) {
            case USERNAME -> USERNAME_PATTERN.matcher(value).matches();
            case PLAIN_TEXT -> PLAIN_TEXT_PATTERN.matcher(value).matches();
            case COMMON_NAME -> COMMON_NAME_PATTERN.matcher(value).matches();
            case FULL_NAME -> FULL_NAME_PATTERN.matcher(value).matches();
            case LATIN_ALNUM -> LATIN_ALNUM_PATTERN.matcher(value).matches();
            default -> true;
        };
    }
}
