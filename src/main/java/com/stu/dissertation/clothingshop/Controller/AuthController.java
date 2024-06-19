package com.stu.dissertation.clothingshop.Controller;

import com.stu.dissertation.clothingshop.Payload.Request.RePasswordRequest;
import com.stu.dissertation.clothingshop.Payload.Request.UserCredentialsRequest;
import com.stu.dissertation.clothingshop.Payload.Response.ResponseMessage;
import com.stu.dissertation.clothingshop.Service.NguoiDung.NguoiDungService;
import com.stu.dissertation.clothingshop.Service.NguoiDung.RegisterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final RegisterService registerService;
    private final NguoiDungService nguoiDungService;
    private final HttpHeaders headers;

    @PostMapping("/login")
    public ResponseEntity<ResponseMessage> loginWithAccount(@RequestBody @Valid UserCredentialsRequest request){
        ResponseMessage response = nguoiDungService.loginWithAccount(request);
        return new ResponseEntity<>(response, headers,OK);
    }
    @PostMapping("/register")
    public ResponseEntity<ResponseMessage> registerWithAccount(@RequestBody @Valid UserCredentialsRequest request)  {
        ResponseMessage response = registerService.register(request);
        return new ResponseEntity<>(response, headers,OK);
    }
    @GetMapping("/validate")
    public ResponseEntity<ResponseMessage> validateAccount(@RequestParam(value="token") String token){
        ResponseMessage response = nguoiDungService.activateAccount(token);
        return new ResponseEntity<>(response, headers, OK);
    }
    @GetMapping("reset-password")
    public ResponseEntity<ResponseMessage> resetPassword(@RequestParam(value="email") String email){
        return null;
    }
    @PostMapping("/reset-password")
    public ResponseEntity<ResponseMessage> resetPassword(@RequestBody RePasswordRequest request){
        return null;
    }
    @PostMapping("/logout")
    public ResponseEntity<ResponseMessage> logout(@RequestBody String token){
        return null;
    }
    @GetMapping("/refresh")
    public ResponseEntity<ResponseMessage> refreshToken(@RequestParam("token") String token) {
        ResponseMessage response = nguoiDungService.refreshToken(token);
        return new ResponseEntity<>(response, headers, OK);
    }
}
