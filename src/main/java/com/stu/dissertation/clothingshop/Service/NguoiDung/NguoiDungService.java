package com.stu.dissertation.clothingshop.Service.NguoiDung;

import com.nimbusds.jose.JOSEException;
import com.stu.dissertation.clothingshop.DAO.NguoiDung.NguoiDungDAO;
import com.stu.dissertation.clothingshop.DTO.NguoiDungDetailDTO;
import com.stu.dissertation.clothingshop.Entities.NguoiDung;
import com.stu.dissertation.clothingshop.Entities.RefreshToken;
import com.stu.dissertation.clothingshop.Enum.BusinessErrorCode;
import com.stu.dissertation.clothingshop.Exception.CustomException.ApplicationException;
import com.stu.dissertation.clothingshop.Payload.Request.UserCredentialsRequest;
import com.stu.dissertation.clothingshop.Payload.Response.ResponseMessage;
import com.stu.dissertation.clothingshop.Security.JWTAuth.JWTService;
import com.stu.dissertation.clothingshop.Service.RefreshToken.RefreshTokenService;
import com.stu.dissertation.clothingshop.Service.UserToken.UserTokenService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;

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

    public ResponseMessage activateAccount(String token){
        try {
       return userTokenService.validateUserToken(token);
        } catch (MessagingException e){
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
                .data(new HashMap<>(){{
                    put("access_token", accessToken);
                    put("refresh_token", refreshToken.getRefreshToken());
                }})
                .build();
        }catch (JOSEException e) {
            throw new ApplicationException(BusinessErrorCode.ACCESS_TOKEN_ERROR, "Error generating token");
        }
    }

    public ResponseMessage refreshToken(String token){
        RefreshToken refresh_token = refreshTokenService.findByToken(token)
                .orElseThrow(()-> new ApplicationException(BusinessErrorCode.INVALID_REFRESH_TOKEN));
        log.warn("Refresh token expired in :" + new Date(refresh_token.getExpiresAt()));
        if(new Date(refresh_token.getExpiresAt()).before(new Date())){
            log.warn("refresh expired");
            throw new ApplicationException(BusinessErrorCode.EXPIRED_REFRESH_TOKEN);
        } else {
            try{
                String accessToken = jwtService.generateToken(refresh_token.getNguoiDung());
                return ResponseMessage.builder()
                       .status(OK)
                       .message("Get new access token successfully")
                       .data(new HashMap<>(){{
                            put("access_token", accessToken);
                        }})
                       .build();
            } catch (JOSEException e) {
                throw new ApplicationException(BusinessErrorCode.ACCESS_TOKEN_ERROR, "Error generating token");
            }
        }
    }

    public ResponseMessage getUserInfo(Long uid){
        NguoiDungDetailDTO user = nguoiDungDAO.findUserById(uid);
        return ResponseMessage.builder()
               .status(OK)
               .message("Get user info successfully")
               .data(new HashMap<>(){{
                   put("user_info", user);
               }})
               .build();
    }
}
