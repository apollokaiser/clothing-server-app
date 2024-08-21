package com.stu.dissertation.clothingshop.Service.NguoiDung;

import com.stu.dissertation.clothingshop.Cache.CacheService.Token.TokenRedisService;
import com.stu.dissertation.clothingshop.Entities.NguoiDung;
import com.stu.dissertation.clothingshop.Entities.UserToken;
import com.stu.dissertation.clothingshop.Enum.BusinessErrorCode;
import com.stu.dissertation.clothingshop.Enum.EmailTemplateEngine;
import com.stu.dissertation.clothingshop.Exception.CustomException.ApplicationException;
import com.stu.dissertation.clothingshop.Payload.Request.UserCredentialsRequest;
import com.stu.dissertation.clothingshop.Payload.Response.ResponseMessage;
import com.stu.dissertation.clothingshop.Repositories.NguoiDungRepository;
import com.stu.dissertation.clothingshop.Service.EmailService.EmailService;
import com.stu.dissertation.clothingshop.Utils.UIDCreator;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.http.HttpStatus.OK;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Lazy})
public class RegisterService {
    private final NguoiDungRepository nguoiDungRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final TokenRedisService tokenRedisService;

    @Transactional
    public ResponseMessage register(UserCredentialsRequest request) {
        // đã cập nhật tìm adminEmail
      Optional<NguoiDung> user =  nguoiDungRepository.findByEmail(request.getEmail());
      if(user.isPresent()) throw new ApplicationException(BusinessErrorCode.USER_ALREADY_EXIST);
      String encodePassword = passwordEncoder.encode(request.getPassword());
      String uid = UIDCreator.createUserCode();
        //save user credentials
        NguoiDung nguoiDung = NguoiDung.builder()
                .id(uid)
                .email(request.getEmail())
                .tenNguoiDung(request.getName())
                .matKhau(encodePassword)
                .enabled(false)
                .khachMoi(false)
                .build();
        String verificationToken = UUID.randomUUID().toString();
       nguoiDungRepository.save(nguoiDung);
        tokenRedisService.saveUserToken(uid, verificationToken);
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
