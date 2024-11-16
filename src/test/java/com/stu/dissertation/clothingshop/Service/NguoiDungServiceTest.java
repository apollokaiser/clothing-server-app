package com.stu.dissertation.clothingshop.Service;

import com.nimbusds.jose.JOSEException;
import com.stu.dissertation.clothingshop.Cache.CacheService.Token.TokenRedisService;
import com.stu.dissertation.clothingshop.Entities.NguoiDung;
import com.stu.dissertation.clothingshop.Entities.RefreshToken;
import com.stu.dissertation.clothingshop.Enum.BusinessErrorCode;
import com.stu.dissertation.clothingshop.Exception.CustomException.ApplicationException;
import com.stu.dissertation.clothingshop.Payload.Request.UserCredentialsRequest;
import com.stu.dissertation.clothingshop.Payload.Response.ResponseMessage;
import com.stu.dissertation.clothingshop.Payload.Response.UserInfo;
import com.stu.dissertation.clothingshop.Repositories.NguoiDungRepository;
import com.stu.dissertation.clothingshop.Security.JWTAuth.JWTService;
import com.stu.dissertation.clothingshop.Service.EmailService.EmailService;
import com.stu.dissertation.clothingshop.Service.ExternalIdentity.GoogleIdentityService;
import com.stu.dissertation.clothingshop.Service.NguoiDung.NguoiDungService;
import com.stu.dissertation.clothingshop.Service.RefreshToken.RefreshTokenService;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.any;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
@Slf4j
public class NguoiDungServiceTest {

    @MockBean
    private NguoiDungRepository nguoiDungRepository;
    @Autowired
    private NguoiDungService nguoiDungService;

    @MockBean
    private TokenRedisService tokenRedisService;

    @MockBean
    private EmailService emailService;

    @MockBean
    private RefreshTokenService refreshTokenService;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private JWTService jwtService;

    @MockBean
    private GoogleIdentityService googleIdentityService;

    private ResponseMessage responseMessage;

    private NguoiDung nguoiDung;

    private static UserInfo userInfo;

    private static RefreshToken refreshToken;

    @BeforeAll
    public static void initGlobalMockData() {
        refreshToken = RefreshToken.builder()
               .refreshToken("test_refresh_token")
               .build();
        userInfo = new UserInfo(
                "google_id_1",
                "test_google",
                "test_google",
                "test_google",
                "test_picture",
                "test@gmail.com",
                true, "vi-VN");
    }
    @BeforeEach
    public void setup() {
        //mock nguoiDung
        nguoiDung = NguoiDung.builder()
               .id("1")
               .tenNguoiDung("test")
               .email("test@test.com")
               .matKhau("test")
               .enabled(true)
               .build();
    }
    @Test
    public void test_activateAccount_withValidCode_successfully(){
        //test with unactivated account
        nguoiDung.setEnabled(false);
        //WHEN
        Mockito.when(nguoiDungRepository.findByEmail("test@test.com"))
                .thenReturn(Optional.of(nguoiDung));
        Mockito.when(tokenRedisService.checkUserToken("test@test.com",ArgumentMatchers.anyString()))
                .thenReturn(true);
        responseMessage = nguoiDungService.activateAccount("test@test.com", "test_token");
        //THEN
        Assertions.assertThat(responseMessage.getData().get("user_id")).isEqualTo("1");
        Assertions.assertThat(responseMessage.getStatus()).isEqualTo(200);
        Assertions.assertThat(responseMessage.getData().get("enabled")).isEqualTo(true);
    }
    @Test
    public void test_activateAccount_wihtInvalidToken_and_UnactivatedAccount() throws MessagingException {
        //test with unactivated account
        nguoiDung.setEnabled(false);
        Mockito.when(nguoiDungRepository.findByEmail(anyString()))
                .thenReturn(Optional.of(nguoiDung));
        // WHEN
        //no token available
        Mockito.when(tokenRedisService.checkUserToken(anyString(), anyString()))
                .thenReturn(false);
        Mockito.doNothing().when(emailService).sendActivationAccount(anyString(), anyString(), any(), any());
        responseMessage = nguoiDungService.activateAccount("test@test.com", "test_token");
        assertThat(responseMessage.getStatus()).isEqualTo(HttpStatus.ACCEPTED.value());
        assertThat(responseMessage.getMessage()).isEqualTo("Token has expired ! Check new token in your mail");
    }
    @Test
    public void test_activateAccount_withActivedAccount(){
        //WHEN
        Mockito.when(nguoiDungRepository.findByEmail(anyString()))
                .thenReturn(Optional.of(nguoiDung));
        var exception = assertThrows(ApplicationException.class,
                ()-> nguoiDungService.activateAccount(anyString(), anyString()));
        //THEN
        assertThat(exception.getErrorCode()).isEqualTo(BusinessErrorCode.USER_ACTIVATED);
    }
    @Test
    public void test_loginWithAccount_withValidEmailPassword_and_enableUser() throws JOSEException {
        UserCredentialsRequest request = UserCredentialsRequest.builder()
                .email("test@test.com")
                .password("test")
                .build();
        Authentication auth = Mockito.mock(Authentication.class);
        //WHEN
        when(authenticationManager.authenticate(any()))
                .thenReturn(auth);
        when((auth.getPrincipal())).thenReturn(nguoiDung);
        when(refreshTokenService.handle(any(),any()))
                .thenReturn(refreshToken);
        doNothing().when(nguoiDungRepository).updatelastLogin(any(),any());
        when(jwtService.generateToken(any()))
                .thenReturn("test_access_token");
        //THEN
        responseMessage = nguoiDungService.loginWithAccount(request);
        assertThat(responseMessage.getMessage()).isEqualTo("Login successfully");
    }
    @Test
    public void test_loginWithAccount_withIncorrectCrendials_Failed() throws JOSEException {
        UserCredentialsRequest request = new UserCredentialsRequest();
        //WHEN
        when(authenticationManager.authenticate(any())).thenThrow(BadCredentialsException.class);
        //THEN
        verify(nguoiDungRepository,never()).updatelastLogin(any(),any());
        verify(refreshTokenService, never()).handle(any(),any());
    }

    @Test
    public void test_loginWithGoogle_withValidGoogleCode() throws JOSEException {
        //preparing a new user that has the same id with userInfo
        nguoiDung = NguoiDung.builder()
                .id("google_id_1")
                .email("test@gmail.com")
                .build();
        //WHEN
        when(googleIdentityService.exchangeUserInfo(anyString()))
                .thenReturn(userInfo);
        when(nguoiDungRepository.findByEmail("test@gmail.com"))
                .thenReturn(Optional.of(nguoiDung));
        when(refreshTokenService.handle(any(),any()))
                .thenReturn(refreshToken);
        doNothing().when(nguoiDungRepository).updatelastLogin(any(),any());
        when(jwtService.generateToken(any())).thenReturn("test_access_token");

        //THEN
        responseMessage = nguoiDungService.loginWithGoogle("testcode");
        assertThat(responseMessage.getMessage()).isEqualTo("Login successfully");
    }
    @Test
    public void test_loginWithGoogle_withRegisteredEmailByUsernamePassword() {
        nguoiDung = NguoiDung.builder()
                .id("1")
                .email("test@gmail.com")
                .build();
        when(googleIdentityService.exchangeUserInfo(anyString()))
                .thenReturn(userInfo);
        when(nguoiDungRepository.findByEmail("test@gmail.com"))
                .thenReturn(Optional.of(nguoiDung));
        var exception = assertThrows(ApplicationException.class,
                ()-> nguoiDungService.loginWithGoogle(anyString()));
        assertThat(exception.getErrorCode()).isEqualTo(BusinessErrorCode.EMAIL_REGISTERED);
    }
}
