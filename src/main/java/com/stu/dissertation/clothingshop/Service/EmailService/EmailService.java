package com.stu.dissertation.clothingshop.Service.EmailService;

import com.stu.dissertation.clothingshop.Enum.EmailTemplateEngine;
import jakarta.mail.MessagingException;

public interface EmailService {
    void sendActivationAccount(
            String to,
            String subject,
            String activationCode,
            EmailTemplateEngine emailTemplateEngine) throws MessagingException;
    void sendResetPasswordCode(
            String to,
            String subject,
            String resetPasswordCode,
            EmailTemplateEngine emailTemplateEngine) throws MessagingException;
}
