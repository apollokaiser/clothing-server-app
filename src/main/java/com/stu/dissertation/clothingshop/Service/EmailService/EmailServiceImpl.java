package com.stu.dissertation.clothingshop.Service.EmailService;

import com.stu.dissertation.clothingshop.Enum.EmailTemplateEngine;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.spring6.SpringTemplateEngine;

import org.thymeleaf.context.Context;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@Lazy
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService{
    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;
    @Override
    @Async
    public void sendActivationAccount(String to, String activationCode, String subject, EmailTemplateEngine emailTemplateEngine) throws MessagingException {
        if(subject == null) {
            subject = "CONFIRM ACCOUNT";
        }
        String template = emailTemplateEngine == null ? "activation_account" : emailTemplateEngine.getName();
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(
                mimeMessage,
                MimeMessageHelper.MULTIPART_MODE_MIXED,
                StandardCharsets.UTF_8.name()
        );
        Map<String, Object> props = new HashMap<>();
        String link = String.format("http://localhost:5173/auth/confirm?email=%s&token=%s",to, activationCode);
        props.put("link", link);
        props.put("to", to);
        Context context = new Context();
        context.setVariable("props", props);
        helper.setFrom("ngothinh123147@gmail.com");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(templateEngine.process(template, context), true);
        javaMailSender.send(mimeMessage);
    }

    @Override
    @Async
    public void sendResetPasswordCode(String to, String resetPasswordCode, String subject, EmailTemplateEngine emailTemplateEngine) throws MessagingException {
        String template = emailTemplateEngine.getName() ==null ? "reset_password" : emailTemplateEngine.getName();
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(
                mimeMessage,
                MimeMessageHelper.MULTIPART_MODE_MIXED,
                StandardCharsets.UTF_8.name()
        );
        Map<String, Object> props = new HashMap<>();
        props.put("code", resetPasswordCode);
        props.put("to", to);
        Context context = new Context();
        context.setVariable("props", props);
        helper.setFrom("ngothinh123147@gmail.com");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(templateEngine.process(template, context), true);
        javaMailSender.send(mimeMessage);
    }

    @Override
    @Async
    public void sendAdminInfo(String to, String subject, String password, String name, String emailSuffix, EmailTemplateEngine emailTemplateEngine) throws MessagingException {
        String template = emailTemplateEngine.getName() ==null ? "send_admin_account" : emailTemplateEngine.getName();
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(
                mimeMessage,
                MimeMessageHelper.MULTIPART_MODE_MIXED,
                StandardCharsets.UTF_8.name()
        );
        Map<String, Object> props = new HashMap<>();
        props.put("name", name);
        props.put("password", password);
        props.put("date", new Date());
        props.put("emailSuffix", emailSuffix);
        Context context = new Context();
        context.setVariable("props", props);
        helper.setFrom("ngothinh123147@gmail.com");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(templateEngine.process(template, context), true);
        javaMailSender.send(mimeMessage);
    }
}
