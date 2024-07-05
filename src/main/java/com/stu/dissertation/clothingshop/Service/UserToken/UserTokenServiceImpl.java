package com.stu.dissertation.clothingshop.Service.UserToken;

import com.stu.dissertation.clothingshop.Entities.UserToken;
import com.stu.dissertation.clothingshop.Enum.BusinessErrorCode;
import com.stu.dissertation.clothingshop.Enum.EmailTemplateEngine;
import com.stu.dissertation.clothingshop.Exception.CustomException.ApplicationException;
import com.stu.dissertation.clothingshop.Payload.Response.ResponseMessage;
import com.stu.dissertation.clothingshop.Repositories.UserTokenRepository;
import com.stu.dissertation.clothingshop.Service.EmailService.EmailService;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

import static org.springframework.http.HttpStatus.OK;

@Service
@RequiredArgsConstructor
public class UserTokenServiceImpl implements UserTokenService {
    private final UserTokenRepository userTokenRepository;
    private final EmailService emailService;

    @Value("${application.security.verification_expired}")
    private long verificationExpired;

    @Override
    @Transactional
    public UserToken findByToken(String token) {
        UserToken userToken = userTokenRepository.findByToken(token)
                .orElseThrow(() -> new ApplicationException(BusinessErrorCode.INVALID_TOKEN));
        if (userToken.getExpiresAt() < Instant.now().toEpochMilli())
            throw new ApplicationException(BusinessErrorCode.EXPIRED_TOKEN);
        return userToken;
    }

    @Override
    @Transactional
    public UserToken saveUserToken(UserToken user_token) throws MessagingException {
        user_token.setExpiresAt(Instant.now().plus(verificationExpired, ChronoUnit.MINUTES).toEpochMilli());
        String subject = "RESET PASSWORD";
        EmailTemplateEngine emailTemplate = EmailTemplateEngine.RESET_PASSWORD;
        emailService.sendResetPasswordCode(
                user_token.getNguoiDung().getEmail(),
                subject, user_token.getToken(), emailTemplate);
        return userTokenRepository.save(user_token);
    }

    @Override
    @Transactional
    public ResponseMessage validateUserToken(String token) throws MessagingException {
        UserToken user_token = userTokenRepository.findByToken(token)
                .orElseThrow(() -> new ApplicationException(BusinessErrorCode.INVALID_TOKEN));
        Date expiration = new Date(user_token.getExpiresAt());
        if (expiration.before(new Date())) {
            EmailTemplateEngine viewEngine = EmailTemplateEngine.ACTIVATION_ACCOUNT;
            String subject = "CONFIRM ACCOUNT";
            String verificationToken = UUID.randomUUID().toString();
            UserToken entity = UserToken.builder()
                    .nguoiDung(user_token.getNguoiDung())
                    .token(verificationToken)
                    .expiresAt(Instant.now().plus(verificationExpired, ChronoUnit.MINUTES).toEpochMilli())
                    .build();
            userTokenRepository.save(entity);
            emailService.sendActivationAccount(user_token.getNguoiDung().getEmail(),
                    subject, verificationToken, viewEngine);
            return ResponseMessage.builder()
                    .status(HttpStatus.ACCEPTED)
                    .message("Token has expired ! Check new token in your mail")
                    .build();
        }
        userTokenRepository.activateUserByToken(token, Instant.now().toEpochMilli());
        return ResponseMessage.builder()
                .status(OK)
                .message("Your account has been activated successfully")
                .build();
    }

    @Override
    @Transactional
    public void validateResetPasswordToken(String token) {
        UserToken userToken = userTokenRepository.findByToken(token).
                orElseThrow(() -> new ApplicationException(BusinessErrorCode.INVALID_TOKEN));
        userToken.setValidateAt(Instant.now().toEpochMilli());
        userTokenRepository.save(userToken);
    }
}
