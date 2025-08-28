package com.trading_simulator.backend.service;

import com.trading_simulator.backend.object.entity.User;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;

    @Value("${EMAIL_AUTHOR}")
    private String sender;

    @Async
    @Override
    public Boolean sendEmailVerification(User user, String verificationUrl) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, StandardCharsets.UTF_8.name());

            // Load template email
            Context context = new Context();
            context.setVariable("author", sender);
            context.setVariable("url", verificationUrl);;
            String html = templateEngine.process("verification-email", context);

            // Send email
            helper.setTo(user.getEmail());
            helper.setText(html, true);
            helper.setSubject("Verification email");
            helper.setFrom(sender);
            javaMailSender.send(message);
            System.out.println("Verification email successful");
            return true;
        } catch (MessagingException exc) {
            System.out.println("Email sent with error: " + exc.getMessage());
            return false;
        }
    }

    @Async
    @Override
    public Boolean sendResetPasswordEmail(User user, String username, String resetPasswordUrl) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, StandardCharsets.UTF_8.name());

            // Load template email
            Context context = new Context();
            context.setVariable("receiver", username);
            context.setVariable("author", sender);
            context.setVariable("content", "Alo alo");
            context.setVariable("url", resetPasswordUrl);;
            String html = templateEngine.process("reset-password-email", context);

            // Send email
            helper.setTo(user.getEmail());
            helper.setText(html, true);
            helper.setSubject("Reset password");
            helper.setFrom(sender);
            javaMailSender.send(message);
            System.out.println("Send reset password email successful");
            return true;
        } catch (MessagingException exc) {
            System.out.println("Email sent with error: " + exc.getMessage());
            return false;
        }
    }

    @Override
    public Boolean sendCustomEmail() {
        return null;
    }
}
