package com.trading_simulator.backend.externalservice;

import com.trading_simulator.backend.domain.auth.Auth;

public interface EmailService {
    Boolean sendEmailVerification(Auth auth, String verificationUrl);
    Boolean sendResetPasswordEmail(Auth auth, String username, String resetPasswordUrl);
    Boolean sendCustomEmail();
}