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
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

import static org.springframework.http.HttpStatus.OK;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Lazy})
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
        if (userToken.getExpiresAt() <= Instant.now().getEpochSecond())
            throw new ApplicationException(BusinessErrorCode.EXPIRED_TOKEN);
        return userToken;
    }

    @Override
    @Transactional
    public void saveUserToken(UserToken user_token) throws MessagingException {
        Long exp = Instant.now().plus(verificationExpired, ChronoUnit.MINUTES).toEpochMilli() /1000;
        user_token.setExpiresAt(exp);
        String subject = "RESET PASSWORD";
        EmailTemplateEngine emailTemplate = EmailTemplateEngine.RESET_PASSWORD;
        emailService.sendResetPasswordCode(
                user_token.getNguoiDung().getEmail(),
                subject, user_token.getToken(), emailTemplate);
        userTokenRepository.save(user_token);
    }

    @Override
    @Transactional
    public ResponseMessage validateUserToken(String token) throws MessagingException {
        UserToken user_token = userTokenRepository.findByToken(token)
                .orElseThrow(() -> new ApplicationException(BusinessErrorCode.INVALID_TOKEN));
        //Kiểm tra tài khoản đã được kích hoạt chưa
        if(user_token.getValidateAt() != null || user_token.getNguoiDung().isEnabled()) {
            throw new ApplicationException(BusinessErrorCode.USER_ACTIVATED);
        }
        Date expiration = new Date(user_token.getExpiresAt());
        //Kiểm tra xem token có hết hạn không
        if (expiration.before(new Date())) { //expiration: true
            EmailTemplateEngine viewEngine = EmailTemplateEngine.ACTIVATION_ACCOUNT;
            String subject = "CONFIRM ACCOUNT";
            String verificationToken = UUID.randomUUID().toString();
            Long exp = Instant.now().plus(verificationExpired, ChronoUnit.MINUTES).toEpochMilli() /1000;
            UserToken entity = UserToken.builder()
                    .nguoiDung(user_token.getNguoiDung())
                    .token(verificationToken)
                    .expiresAt(exp)
                    .build();
            userTokenRepository.save(entity);
            emailService.sendActivationAccount(user_token.getNguoiDung().getEmail(),
                    subject, verificationToken, viewEngine);
            return ResponseMessage.builder()
                    .status(HttpStatus.ACCEPTED)
                    .message("Token has expired ! Check new token in your mail")
                    .build();
        }
        int result = userTokenRepository.activateUserByToken(token, Instant.now().getEpochSecond());
        if(result== 0) throw new ApplicationException(BusinessErrorCode.ACTIVATION_ACCOUNT_FAILED);
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
        userToken.setValidateAt(Instant.now().getEpochSecond());
        userTokenRepository.save(userToken);
    }
}
