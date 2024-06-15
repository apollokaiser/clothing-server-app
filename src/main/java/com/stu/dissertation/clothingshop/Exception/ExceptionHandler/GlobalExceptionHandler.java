package com.stu.dissertation.clothingshop.Exception.ExceptionHandler;

import com.stu.dissertation.clothingshop.Enum.BusinessErrorCode;
import com.stu.dissertation.clothingshop.Exception.CustomException.ApplicationException;
import com.stu.dissertation.clothingshop.Payload.Response.ResponseMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.http.HttpStatus.OK;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(LockedException.class)
    public ResponseEntity<ResponseMessage> handleLockedException(LockedException e){
        ResponseMessage response = ResponseMessage.errorBuilder()
                .errorCode(BusinessErrorCode.USER_NOT_ACTIVATED)
                .build();
        return new ResponseEntity<>(response, OK);
    }
    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ResponseMessage> handleApplicationException(ApplicationException e){
        ResponseMessage response = ResponseMessage.errorBuilder()
               .errorCode(e.getErrorCode())
                .message(e.getMessage())
               .build();
        return new ResponseEntity<>(response, OK);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseMessage> handleException(Exception e){
        ResponseMessage response = ResponseMessage.errorBuilder()
               .errorCode(BusinessErrorCode.INTERNAL_ERROR)
               .message(e.getMessage())
               .build();
        return new ResponseEntity<>(response, OK);
    }
}
