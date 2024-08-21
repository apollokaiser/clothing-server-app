package com.stu.dissertation.clothingshop.Service.UserToken;

import com.stu.dissertation.clothingshop.Entities.UserToken;
import com.stu.dissertation.clothingshop.Payload.Response.ResponseMessage;
import jakarta.mail.MessagingException;

@Deprecated(forRemoval = true)
public interface UserTokenService {
    UserToken findByToken(String token);
    void saveUserToken(UserToken user_token) throws MessagingException;
    ResponseMessage validateUserToken(String token) throws MessagingException;
    void validateResetPasswordToken(String token);
}
