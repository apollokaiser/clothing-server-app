package com.stu.dissertation.clothingshop.Service.EmailService;

import com.stu.dissertation.clothingshop.Enum.EmailTemplateEngine;
import jakarta.mail.MessagingException;

public interface EmailService {
    void sendActivationAccount(
            String to,
            String activationCode,
            String subject,
            EmailTemplateEngine emailTemplateEngine) throws MessagingException;
    void sendResetPasswordCode(
            String to,
            String resetPasswordCode,
            String subject,
            EmailTemplateEngine emailTemplateEngine) throws MessagingException;
    void sendAdminInfo(
            String to,
            String subject,
            String password,
            String name,
            String emailSuffix,
            EmailTemplateEngine emailTemplateEngine) throws MessagingException;
}
