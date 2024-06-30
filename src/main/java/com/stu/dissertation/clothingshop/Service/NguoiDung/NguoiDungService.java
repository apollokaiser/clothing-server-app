package com.stu.dissertation.clothingshop.Service.NguoiDung;

import com.nimbusds.jose.JOSEException;
import com.stu.dissertation.clothingshop.DAO.NguoiDung.NguoiDungDAO;
import com.stu.dissertation.clothingshop.DTO.NguoiDungDetailDTO;
import com.stu.dissertation.clothingshop.Entities.NguoiDung;
import com.stu.dissertation.clothingshop.Entities.RefreshToken;
import com.stu.dissertation.clothingshop.Entities.TaiKhoanLienKet;
import com.stu.dissertation.clothingshop.Entities.UserToken;
import com.stu.dissertation.clothingshop.Enum.BusinessErrorCode;
import com.stu.dissertation.clothingshop.Enum.LoginType;
import com.stu.dissertation.clothingshop.Exception.CustomException.ApplicationException;
import com.stu.dissertation.clothingshop.Payload.Request.RePasswordRequest;
import com.stu.dissertation.clothingshop.Payload.Request.UserCredentialsRequest;
import com.stu.dissertation.clothingshop.Payload.Response.ResponseMessage;
import com.stu.dissertation.clothingshop.Payload.Response.UserInfo;
import com.stu.dissertation.clothingshop.Security.JWTAuth.JWTService;
import com.stu.dissertation.clothingshop.Service.ExternalIdentity.GoogleIdentityService;
import com.stu.dissertation.clothingshop.Service.RefreshToken.RefreshTokenService;
import com.stu.dissertation.clothingshop.Service.UserToken.UserTokenService;
import com.stu.dissertation.clothingshop.Utils.RandomCodeGenerator;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

import static org.springframework.http.HttpStatus.OK;

@Service
@RequiredArgsConstructor
@Slf4j
public class NguoiDungService {
    private final JWTService jwtService;
    private final NguoiDungDAO nguoiDungDAO;
    private final UserTokenService userTokenService;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;
    private final GoogleIdentityService googleIdentityService;

    public ResponseMessage activateAccount(String token) {
        try {
            return userTokenService.validateUserToken(token);
        } catch (MessagingException e) {
            throw new ApplicationException((BusinessErrorCode.ERROR_MAIL));
        }
    }

    public ResponseMessage loginWithAccount(UserCredentialsRequest request) {
        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        NguoiDung user = (NguoiDung) auth.getPrincipal();
        RefreshToken refreshToken = refreshTokenService.handle(user, user.getRefreshToken());
        try {
            String accessToken = jwtService.generateToken(user);
            return ResponseMessage.builder()
                    .status(OK)
                    .message("Login successfully")
                    .data(new HashMap<>() {{
                        put("access_token", accessToken);
                        put("refresh_token", refreshToken.getRefreshToken());
                    }})
                    .build();
        } catch (JOSEException e) {
            throw new ApplicationException(BusinessErrorCode.ACCESS_TOKEN_ERROR, "Error generating token");
        }
    }

    public ResponseMessage refreshToken(String token) {
        RefreshToken refresh_token = refreshTokenService.findByToken(token)
                .orElseThrow(() -> new ApplicationException(BusinessErrorCode.INVALID_REFRESH_TOKEN));
        if (new Date(refresh_token.getExpiresAt()).before(new Date())) {
            log.warn("refresh expired");
            throw new ApplicationException(BusinessErrorCode.EXPIRED_REFRESH_TOKEN);
        } else {
            try {
                String accessToken = jwtService.generateToken(refresh_token.getNguoiDung());
                return ResponseMessage.builder()
                        .status(OK)
                        .message("Get new access token successfully")
                        .data(new HashMap<>() {{
                            put("access_token", accessToken);
                        }})
                        .build();
            } catch (JOSEException e) {
                throw new ApplicationException(BusinessErrorCode.ACCESS_TOKEN_ERROR, "Error generating token");
            }
        }
    }

    public ResponseMessage getUserInfo(String uid) {
        NguoiDungDetailDTO user = nguoiDungDAO.findUserById(uid);
        return ResponseMessage.builder()
                .status(OK)
                .message("Get user info successfully")
                .data(new HashMap<>() {{
                    put("user_info", user);
                }})
                .build();
    }

    public ResponseMessage resetPassword(String email) throws MessagingException {
        NguoiDung nguoiDung = nguoiDungDAO.findNguoiDungByEmail(email)
                .orElseThrow(() -> new ApplicationException(BusinessErrorCode.USER_NOT_FOUND));
        if(nguoiDung.getMatKhau() ==null)
            throw new ApplicationException(BusinessErrorCode.USER_NOT_FOUND);
        String token = RandomCodeGenerator.generateRandomCode(7);
        UserToken userToken = UserToken.builder()
                .token(token)
                .nguoiDung(nguoiDung)
                .build();
        userTokenService.saveUserToken(userToken);
        return ResponseMessage.builder()
                .status(OK)
                .message("Your request was successfully")
                .build();
    }

    @Transactional
    public ResponseMessage resetPassword(RePasswordRequest request) {
        UserToken userToken = userTokenService.findByToken(request.getResetCode());
        NguoiDung nguoiDung = userToken.getNguoiDung();
        nguoiDungDAO.resetPassword(nguoiDung.getEmail(), request.getNewPassword());
        userTokenService.validateResetPasswordToken(request.getResetCode());
        return ResponseMessage.builder()
                .status(OK)
                .message("Reset password successfully")
                .build();
    }

    @Transactional
    public ResponseMessage loginWithGoogle(String authCode) {
        UserInfo userInfo = googleIdentityService.exchangeUserInfo(authCode);
        Optional<NguoiDung> nguoiDung = nguoiDungDAO.findNguoiDungByEmail(userInfo.email());
        //người dùng chưa từng có account bằng email này trước đây --> tạo tài khoản mới
        if (nguoiDung.isEmpty()) {
            return signupWithGoogle(userInfo);
        } else {
            if (Objects.equals(nguoiDung.get().getId(), userInfo.id())) {
                RefreshToken refreshToken = refreshTokenService.handle(nguoiDung.get(), nguoiDung.get().getRefreshToken());
                try {
                    String accesstoken = jwtService.generateToken(nguoiDung.get());
                    return ResponseMessage.builder()
                            .status(OK)
                            .message("Login successfully")
                            .data(new HashMap<>() {{
                                put("access_token", accesstoken);
                                put("refresh_token", refreshToken.getRefreshToken());
                            }})
                            .build();
                } catch (JOSEException e) {
                    throw new ApplicationException(BusinessErrorCode.ACCESS_TOKEN_ERROR);
                }
            }
            throw new ApplicationException(BusinessErrorCode.EMAIL_REGISTERED);
        }
    }

    @Transactional
    private ResponseMessage signupWithGoogle(UserInfo userInfo) {
        // tạo người dùng
        NguoiDung userEntity = NguoiDung.builder()
                .id(userInfo.id())
                .email(userInfo.email())
                .tenNguoiDung(userInfo.name())
                .enabled(true)
                .khachMoi(true)
                .build();
        //tạo tài khoản
        TaiKhoanLienKet taiKhoanLienKet = TaiKhoanLienKet.builder()
                .nguoiDung(userEntity)
                .provider(LoginType.GOOGLE)
                .build();
        //liên kết người dùng và tài khoản
        userEntity.setTaiKhoan(taiKhoanLienKet);
        //lưu người dùng và tài khoản
        NguoiDung nguoiDung = nguoiDungDAO.save(userEntity);
        //tạo refresh token
        RefreshToken refreshToken = refreshTokenService.handle(nguoiDung, null);
        try {
            //tạo access token
            String accessToken = jwtService.generateToken(nguoiDung);
            return ResponseMessage.builder()
                    .status(HttpStatus.OK)
                    .message("Login successfully")
                    .data(new HashMap<>() {{
                        put("access_token", accessToken);
                        put("refresh_token", refreshToken.getRefreshToken());
                    }})
                    .build();
        } catch (JOSEException e) {
            throw new ApplicationException(BusinessErrorCode.ACCESS_TOKEN_ERROR);
        }

    }
}
