package com.trading_simulator.backend.service;

import com.trading_simulator.backend.object.entity.Auth;

public interface EmailService {
    Boolean sendEmailVerification(Auth auth, String verificationUrl);
    Boolean sendResetPasswordEmail(Auth auth, String username, String resetPasswordUrl);
    Boolean sendCustomEmail();
}