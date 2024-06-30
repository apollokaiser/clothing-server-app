package com.stu.dissertation.clothingshop.Service.RefreshToken;

import com.stu.dissertation.clothingshop.DAO.RefreshToken.RefreshTokenDAO;
import com.stu.dissertation.clothingshop.Entities.NguoiDung;
import com.stu.dissertation.clothingshop.Entities.RefreshToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class RefreshTokenServiceImpl implements RefreshTokenService{
    private final RefreshTokenDAO refreshTokenDAO;
    @Value("${application.security.jwt.expiration-refresh-token}")
    private long expirationRefresh;
    @Override
    public RefreshToken createRefreshToken(NguoiDung user) {
        if (user == null) return null;
        String token = UUID.randomUUID().toString();
        Long expiration  = Instant.now().plus(expirationRefresh, ChronoUnit.HOURS).toEpochMilli();
        token = token + "-" + expiration;
        return RefreshToken.builder()
                .refreshToken(token)
                .expiresAt(expiration)
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
        } else if(new Date(refreshToken.getExpiresAt()).before(new Date())) {
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
        refreshTokenDAO.save(entity);
    }

    @Override
    public void delete(RefreshToken entity) {

    }

    @Override
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenDAO.findByToken(token);
    }

    @Override
    public void update(RefreshToken entity) {

    }
}
