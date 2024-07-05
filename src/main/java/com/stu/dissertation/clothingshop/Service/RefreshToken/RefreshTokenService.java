package com.stu.dissertation.clothingshop.Service.RefreshToken;

import com.stu.dissertation.clothingshop.Entities.NguoiDung;
import com.stu.dissertation.clothingshop.Entities.RefreshToken;

import java.util.Optional;

public interface RefreshTokenService {
    RefreshToken createRefreshToken(NguoiDung user);
    RefreshToken handle(NguoiDung user,RefreshToken refreshToken);
    RefreshToken save(RefreshToken entity);
    Optional<RefreshToken> findByToken(String token);
    RefreshToken update(RefreshToken entity);
}
