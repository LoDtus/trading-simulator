package com.trading_simulator.backend.config.exception;

import com.trading_simulator.backend.object.dto.common.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

//@ControllerAdvice
//public class GlobalExceptionConfig {
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<ApiResponse<?>> handleValidationErrors(MethodArgumentNotValidException ex) {
//        String message = ex.getBindingResult().getFieldErrors().stream()
//                .map(error -> error.getField() + ": " + error.getDefaultMessage())
//                .findFirst()
//                .orElse("Validation failed");
//
//        ApiResponse<?> response = ApiResponse.builder()
//                .statusCode(HttpStatus.BAD_REQUEST.value())
//                .errorCode("ERR_VALIDATION") // mã lỗi riêng của hệ thống
//                .message(message)
//                .timestamp(Instant.now())
//                .build();
//
//        return ResponseEntity.badRequest().body(response);
//    }
//}