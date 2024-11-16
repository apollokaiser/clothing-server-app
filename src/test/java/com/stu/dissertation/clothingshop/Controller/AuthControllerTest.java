package com.stu.dissertation.clothingshop.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stu.dissertation.clothingshop.Payload.Request.UserCredentialsRequest;
import com.stu.dissertation.clothingshop.Payload.Response.ResponseMessage;
import com.stu.dissertation.clothingshop.Service.NguoiDung.RegisterService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@Slf4j
@AutoConfigureMockMvc
@TestPropertySource("/test.properties")
public class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private RegisterService registerService;

    private UserCredentialsRequest userCredentialsRequest;

    private ResponseMessage responseMessage;

    @BeforeEach
    private void setupData() throws Exception {
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
    void registerWithAccount_validRequest_success() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(userCredentialsRequest);

        Mockito.when(registerService.register(ArgumentMatchers.any())).thenReturn(responseMessage);

        mockMvc.perform(MockMvcRequestBuilders.
                post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("status")
                .value(200));
    }
}
