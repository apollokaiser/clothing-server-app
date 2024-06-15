package com.stu.dissertation.clothingshop.DAO.RefreshToken;

import com.stu.dissertation.clothingshop.DAO.GenericDAO;
import com.stu.dissertation.clothingshop.Entities.RefreshToken;

import java.util.Optional;

public interface RefreshTokenDAO extends GenericDAO<RefreshToken, String> {
    Optional<RefreshToken> findByToken(String token);
}
