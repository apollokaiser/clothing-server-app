package com.stu.dissertation.clothingshop.DAO.UserTokenDAO;

import com.stu.dissertation.clothingshop.DAO.GenericDAO;
import com.stu.dissertation.clothingshop.Entities.UserToken;

import java.util.Optional;

public interface UserTokenDAO extends GenericDAO<UserToken, Long> {
    Optional<UserToken> findByToken(String token);
    boolean validatedToken(String token);
    void validateResetPasswordToken(String email, String token);
}
