package com.trading_simulator.backend.service;

import com.trading_simulator.backend.object.entity.User;

public interface EmailService {
    Boolean sendEmailVerification(User user, String verificationUrl);
    Boolean sendResetPasswordEmail(User user, String username, String resetPasswordUrl);
    Boolean sendCustomEmail();
}