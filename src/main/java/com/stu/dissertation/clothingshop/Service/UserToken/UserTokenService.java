package com.stu.dissertation.clothingshop.Service.UserToken;

import com.stu.dissertation.clothingshop.Entities.UserToken;
import com.stu.dissertation.clothingshop.Payload.Response.ResponseMessage;
import jakarta.mail.MessagingException;

public interface UserTokenService {
    void saveUserToken(UserToken user_token);
    ResponseMessage validateUserToken(String token) throws MessagingException;
    void validateResetPasswordToken(String email, String token);
}
