package com.stu.dissertation.clothingshop.Service.NguoiDung;

import com.nimbusds.jose.JOSEException;
import com.stu.dissertation.clothingshop.DAO.NguoiDung.NguoiDungDAO;
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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.HashMap;

import static org.springframework.http.HttpStatus.OK;

@Service
@RequiredArgsConstructor
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
}
