package com.stu.dissertation.clothingshop.Service.RefreshToken;

import com.stu.dissertation.clothingshop.Entities.NguoiDung;
import com.stu.dissertation.clothingshop.Entities.RefreshToken;
import com.stu.dissertation.clothingshop.Enum.BusinessErrorCode;
import com.stu.dissertation.clothingshop.Exception.CustomException.ApplicationException;
import com.stu.dissertation.clothingshop.Repositories.NguoiDungRepository;
import com.stu.dissertation.clothingshop.Repositories.RefreshTokenRepository;
import jakarta.transaction.Transactional;
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
public class RefreshTokenServiceImpl implements RefreshTokenService{
    private final RefreshTokenRepository refreshTokenRepository;
    private final NguoiDungRepository nguoiDungRepository;
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
    @Transactional
    public RefreshToken handle(NguoiDung user, RefreshToken refreshToken) {
        //the first time login
        if(refreshToken == null){
            RefreshToken token = createRefreshToken(user);
          return this.save(token);
        } else if(new Date(refreshToken.getExpiresAt()).before(new Date())) {
            RefreshToken token = createRefreshToken(user);
            refreshToken.setRefreshToken(token.getRefreshToken());
            refreshToken.setExpiresAt(token.getExpiresAt());
        return this.update(refreshToken);
        }
        return refreshToken;

    }
    @Override
    @Transactional
    public RefreshToken save(RefreshToken entity) {
        NguoiDung nguoiDung = nguoiDungRepository.findByEmail(entity.getNguoiDung().getEmail())
                        .orElseThrow(()-> new ApplicationException(BusinessErrorCode.USER_NOT_FOUND));
        entity.setNguoiDung(nguoiDung);
        return refreshTokenRepository.save(entity);
    }

    @Override
    @Transactional
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByRefreshToken(token);
    }

    @Override
    @Transactional
    public RefreshToken update(RefreshToken entity) {
        RefreshToken refreshToken = refreshTokenRepository.findById(entity.getId())
                .orElseThrow(()-> new ApplicationException(BusinessErrorCode.INVALID_REFRESH_TOKEN));
        refreshToken.setRefreshToken(entity.getRefreshToken());
        refreshToken.setExpiresAt(entity.getExpiresAt());
        return refreshTokenRepository.save(refreshToken);
    }
}
