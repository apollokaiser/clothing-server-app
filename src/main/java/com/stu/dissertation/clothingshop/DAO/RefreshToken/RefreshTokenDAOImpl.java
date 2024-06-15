package com.stu.dissertation.clothingshop.DAO.RefreshToken;

import com.stu.dissertation.clothingshop.DAO.NguoiDung.NguoiDungDAO;
import com.stu.dissertation.clothingshop.Entities.NguoiDung;
import com.stu.dissertation.clothingshop.Entities.RefreshToken;
import com.stu.dissertation.clothingshop.Enum.BusinessErrorCode;
import com.stu.dissertation.clothingshop.Exception.CustomException.ApplicationException;
import com.stu.dissertation.clothingshop.Repositories.NguoiDungRepository;
import com.stu.dissertation.clothingshop.Repositories.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RefreshTokenDAOImpl implements RefreshTokenDAO{
    private final RefreshTokenRepository refreshTokenRepository;
    private final NguoiDungRepository nguoiDungRepository;
    @Override
    public Optional<RefreshToken> findById(String id) {
        return Optional.empty();
    }

    @Override
    public RefreshToken save(RefreshToken entity) {
        NguoiDung user = nguoiDungRepository.findByEmail(entity.getNguoiDung().getEmail())
                .orElseThrow(()-> new ApplicationException(BusinessErrorCode.USER_NOT_FOUND));
        entity.setNguoiDung(user);
        return refreshTokenRepository.save(entity);
    }

    @Override
    public RefreshToken update(RefreshToken entity) {
        RefreshToken refreshToken = refreshTokenRepository.findById(entity.getId())
                .orElseThrow(()-> new ApplicationException(BusinessErrorCode.INVALID_REFRESH_TOKEN));
        refreshToken.setRefreshToken(entity.getRefreshToken());
        refreshToken.setExpiresAt(entity.getExpiresAt());
        return refreshTokenRepository.save(refreshToken);
    }

    @Override
    public void delete(List<String> ids) {

    }

    @Override
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByRefreshToken(token);
    }
}
