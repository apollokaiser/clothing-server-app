package com.stu.dissertation.clothingshop.Service;

import com.stu.dissertation.clothingshop.Cache.CacheService.Token.TokenRedisService;
import com.stu.dissertation.clothingshop.Entities.NguoiDung;
import com.stu.dissertation.clothingshop.Enum.BusinessErrorCode;
import com.stu.dissertation.clothingshop.Exception.CustomException.ApplicationException;
import com.stu.dissertation.clothingshop.Payload.Request.UserCredentialsRequest;
import com.stu.dissertation.clothingshop.Payload.Response.ResponseMessage;
import com.stu.dissertation.clothingshop.Repositories.NguoiDungRepository;
import com.stu.dissertation.clothingshop.Service.EmailService.EmailService;
import com.stu.dissertation.clothingshop.Service.NguoiDung.RegisterService;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import java.util.Optional;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertThrows;
@SpringBootTest
@Slf4j
public class RegisterServiceTest {

    @Autowired
    private RegisterService registerService;

    @MockBean
    private NguoiDungRepository nguoiDungRepository;

    @MockBean
    private EmailService emailService;

    @MockBean
    private TokenRedisService tokenRedisService;

    private UserCredentialsRequest userCredentialsRequest;

    private ResponseMessage responseMessage;
    private NguoiDung nguoiDung = NguoiDung.builder()
            .email("ngothinh123147@gmail.com")
            .tenNguoiDung("thinh")
            .build();


    @BeforeEach
    public void setupData() throws Exception {
        userCredentialsRequest = UserCredentialsRequest.builder()
                .email("ngothinh123147@gmail.com")
                .name("thinh")
                .password("Thinhpro123")
                .build();
        responseMessage = ResponseMessage.builder()
                .status(HttpStatus.OK)
                .message("Test OK")
                .build();
    }
    @Test
    public void register_validRequest_success() {
        //GIVEN
        Mockito.when(nguoiDungRepository.findByEmail(ArgumentMatchers.anyString()))
                .thenReturn(Optional.empty());
        //WHEN
        var response = registerService.register(ArgumentMatchers.any());
        Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }
    @Test
    public void register_withAlreadyExists_throwException() {
        //GIVEN
        Mockito.when(nguoiDungRepository.findByEmail(ArgumentMatchers.anyString()))
                .thenReturn(Optional.of(nguoiDung));
        //WHEN
        var ex = assertThrows(ApplicationException.class, ()-> {
            registerService.register(ArgumentMatchers.any());
        });
        //THEN
        Assertions.assertThat(ex.getErrorCode()).isEqualTo(BusinessErrorCode.USER_ALREADY_EXIST);
        Mockito.verify(nguoiDungRepository.save(ArgumentMatchers.any()), Mockito.never());
    }
}
