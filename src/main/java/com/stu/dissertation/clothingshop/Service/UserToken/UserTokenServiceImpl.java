package com.stu.dissertation.clothingshop.Service.UserToken;

import com.stu.dissertation.clothingshop.DAO.UserTokenDAO.UserTokenDAO;
import com.stu.dissertation.clothingshop.Entities.UserToken;
import com.stu.dissertation.clothingshop.Enum.BusinessErrorCode;
import com.stu.dissertation.clothingshop.Enum.EmailTemplateEngine;
import com.stu.dissertation.clothingshop.Exception.CustomException.ApplicationException;
import com.stu.dissertation.clothingshop.Payload.Response.ResponseMessage;
import com.stu.dissertation.clothingshop.Service.EmailService.EmailService;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

import static org.springframework.http.HttpStatus.OK;

@Service
@RequiredArgsConstructor
public class UserTokenServiceImpl implements UserTokenService{
    private final UserTokenDAO userTokenDAO;

    private final EmailService emailService;

    @Value("${application.security.verification_expired}")
    private long verificationExpired;
    @Override
    public void saveUserToken(UserToken user_token) {

    }

    @Override
    @Transactional
    public ResponseMessage validateUserToken(String token) throws MessagingException {
        UserToken user_token = userTokenDAO.findByToken(token)
                .orElseThrow(() -> new ApplicationException(BusinessErrorCode.INVALID_TOKEN));
        Date expiration = new Date(user_token.getExpiresAt());
        if(expiration.before(new Date())){
            EmailTemplateEngine viewEngine = EmailTemplateEngine.ACTIVATION_ACCOUNT;
            String subject = "CONFIRM ACCOUNT";
            String verificationToken = UUID.randomUUID().toString();
            emailService.sendActivationAccount(user_token.getNguoiDung().getEmail(),
                    subject,verificationToken,viewEngine);
            UserToken entity = UserToken.builder()
                    .nguoiDung(user_token.getNguoiDung())
                    .token(verificationToken)
                    .expiresAt(Instant.now().plus(verificationExpired, ChronoUnit.MINUTES).toEpochMilli())
                    .build();
            userTokenDAO.save(entity);
            throw new ApplicationException(BusinessErrorCode.EXPIRED_TOKEN,"Token has expired ! Check new token in your mail");
        }
       boolean isActive =  userTokenDAO.validatedToken(token);
        if(!isActive){
            throw new ApplicationException(BusinessErrorCode.INVALID_TOKEN);
        }
        return ResponseMessage.builder()
                .status(OK)
                .message("Your account has been activated successfully")
                .build();
    }

    @Override
    public void validateResetPasswordToken(String email, String token) {

    }
}
