package com.stu.dissertation.clothingshop.Service.NguoiDung;

import com.stu.dissertation.clothingshop.DAO.NguoiDung.NguoiDungDAO;
import com.stu.dissertation.clothingshop.Entities.NguoiDung;
import com.stu.dissertation.clothingshop.Entities.UserToken;
import com.stu.dissertation.clothingshop.Enum.BusinessErrorCode;
import com.stu.dissertation.clothingshop.Enum.EmailTemplateEngine;
import com.stu.dissertation.clothingshop.Exception.CustomException.ApplicationException;
import com.stu.dissertation.clothingshop.Payload.Request.UserCredentialsRequest;
import com.stu.dissertation.clothingshop.Payload.Response.ResponseMessage;
import com.stu.dissertation.clothingshop.Service.EmailService.EmailService;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.HashSet;
import java.util.UUID;

import static org.springframework.http.HttpStatus.OK;

@Service
@Slf4j
@RequiredArgsConstructor
public class RegisterService {
    private final NguoiDungDAO nguoiDungDAO;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    @Value("${application.security.verification_expired}")
    private Long verificationExpired;

    @Transactional
    public ResponseMessage register(UserCredentialsRequest request) {
        if(nguoiDungDAO.isExistUser(request.getEmail()))
            throw new ApplicationException(BusinessErrorCode.USER_ALREADY_EXIST);
        String encodePassword = passwordEncoder.encode(request.getPassword());
        //save user credentials
        NguoiDung nguoiDung = NguoiDung.builder()
                .id(UUID.randomUUID().toString())
                .email(request.getEmail())
                .matKhau(encodePassword)
                .enabled(false)
                .build();
        String verificationToken = UUID.randomUUID().toString();
        UserToken userToken = UserToken.builder()
                .token(verificationToken)
                .expiresAt(Instant.now().plus(verificationExpired, ChronoUnit.MINUTES).toEpochMilli())
                .nguoiDung(nguoiDung)
                .build();
        HashSet<UserToken> tokens = new HashSet<>(){{
            add(userToken);
        }};
        nguoiDung.setUserTokens(tokens);
        nguoiDungDAO.save(nguoiDung);
        //send email
        try {
        sendVerificationEmail(request.getEmail(), verificationToken);
        } catch (MessagingException e) {
            throw new ApplicationException(BusinessErrorCode.ERROR_MAIL);
        }
        return ResponseMessage.builder()
                .status(OK)
                .message("Register successfully")
                .build();

    }
    private void sendVerificationEmail(String mail,
                                       String confirmationToken) throws MessagingException {
        EmailTemplateEngine viewEngine = EmailTemplateEngine.ACTIVATION_ACCOUNT;
        String subject = "CONFIRM ACCOUNT";
        emailService.sendActivationAccount(mail,subject,confirmationToken,viewEngine);
    }
}
