package com.stu.dissertation.clothingshop.Service.RefreshToken;

import com.stu.dissertation.clothingshop.Entities.NguoiDung;
import com.stu.dissertation.clothingshop.Entities.RefreshToken;

import java.util.Optional;

public interface RefreshTokenService {
    RefreshToken createRefreshToken(NguoiDung user);
    RefreshToken handle(NguoiDung user,RefreshToken refreshToken);
    boolean checkRefreshToken(RefreshToken refreshToken);
    void save(RefreshToken entity);
    void delete(RefreshToken entity);
    Optional<RefreshToken> findByToken(String token);
    void update(RefreshToken entity);
}
