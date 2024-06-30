package com.stu.dissertation.clothingshop.Exception.ExceptionHandler;

import com.stu.dissertation.clothingshop.Enum.BusinessErrorCode;
import com.stu.dissertation.clothingshop.Exception.CustomException.ApplicationException;
import com.stu.dissertation.clothingshop.Payload.Response.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.LockedException;

import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.http.HttpStatus.OK;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private final HttpHeaders headers;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseMessage> handleException(Exception e){
        ResponseMessage response = ResponseMessage.errorBuilder()
                .errorCode(BusinessErrorCode.INTERNAL_ERROR)
                .message(e.getMessage())
                .handle();
        return new ResponseEntity<>(response,headers, OK);
    }
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ResponseMessage> handleException(RuntimeException e){
        ResponseMessage response = ResponseMessage.errorBuilder()
                .errorCode(BusinessErrorCode.INTERNAL_ERROR)
                .message(e.getMessage())
                .handle();
        return new ResponseEntity<>(response,headers, OK);
    }
    @ExceptionHandler({AuthenticationServiceException.class, JwtException.class})
    public ResponseEntity<ResponseMessage> handleException(AuthenticationServiceException e ){
            ResponseMessage message = ResponseMessage.builder()
                    .status(HttpStatus.UNAUTHORIZED)
                    .message("You do not have permission to access this resource")
                    .build();
            return new ResponseEntity<>(message, headers, OK);
    }
    @ExceptionHandler(LockedException.class)
    public ResponseEntity<ResponseMessage> handleLockedException(){
        ResponseMessage response = ResponseMessage.errorBuilder()
                .errorCode(BusinessErrorCode.USER_NOT_ACTIVATED)
                .handle();
        return new ResponseEntity<>(response,headers, OK);
    }
    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ResponseMessage> handleApplicationException(ApplicationException e){
        ResponseMessage response = ResponseMessage.errorBuilder()
               .errorCode(e.getErrorCode())
                .message(e.getMessage())
               .handle();
        return new ResponseEntity<>(response,headers, OK);
    }
}
