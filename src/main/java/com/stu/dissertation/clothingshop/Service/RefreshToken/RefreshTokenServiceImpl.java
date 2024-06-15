package com.stu.dissertation.clothingshop.Service.RefreshToken;

import com.stu.dissertation.clothingshop.DAO.RefreshToken.RefreshTokenDAO;
import com.stu.dissertation.clothingshop.Entities.NguoiDung;
import com.stu.dissertation.clothingshop.Entities.RefreshToken;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService{
    private final RefreshTokenDAO refreshTokenDAO;
    @Value("${application.security.jwt.expiration-refresh-token}")
    private long expirationRefresh;
    @Override
    public RefreshToken createRefreshToken(NguoiDung user) {
        if (user == null) return null;
        String token = UUID.randomUUID().toString();
        return RefreshToken.builder()
                .refreshToken(token)
                .expiresAt(Instant.now().plus(expirationRefresh, ChronoUnit.DAYS).toEpochMilli())
                .nguoiDung(user)
                .build();
    }

    @Override
    public RefreshToken handle(NguoiDung user, RefreshToken refreshToken) {
        //the first time login
        if(refreshToken == null){
            RefreshToken token = createRefreshToken(user);
            save(token);
            return token;
        } else if(new Date(refreshToken.getExpiresAt()).after(new Date())) {
            RefreshToken token = createRefreshToken(user);
            refreshToken.setRefreshToken(token.getRefreshToken());
            refreshToken.setExpiresAt(token.getExpiresAt());
            refreshTokenDAO.update(refreshToken);
        }
        return refreshToken;

    }

    @Override
    public boolean checkRefreshToken(RefreshToken refreshToken) {
        return false;
    }

    @Override
    public void save(RefreshToken entity) {

    }

    @Override
    public void delete(RefreshToken entity) {

    }

    @Override
    public Optional<RefreshToken> findByToken(String token) {
        return Optional.empty();
    }

    @Override
    public void update(RefreshToken entity) {

    }
}
