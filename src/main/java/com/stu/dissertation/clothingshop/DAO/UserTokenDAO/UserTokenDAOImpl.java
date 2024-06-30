package com.stu.dissertation.clothingshop.DAO.UserTokenDAO;

import com.stu.dissertation.clothingshop.Entities.NguoiDung;
import com.stu.dissertation.clothingshop.Entities.UserToken;
import com.stu.dissertation.clothingshop.Enum.BusinessErrorCode;
import com.stu.dissertation.clothingshop.Exception.CustomException.ApplicationException;
import com.stu.dissertation.clothingshop.Repositories.NguoiDungRepository;
import com.stu.dissertation.clothingshop.Repositories.UserTokenRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserTokenDAOImpl implements UserTokenDAO{
    private final UserTokenRepository userTokenRepository;
    private final NguoiDungRepository nguoiDungRepository;

    @Override
    public  Optional<UserToken> findByToken(String token) {
        return userTokenRepository.findByToken(token);
    }

    @Override
    public boolean validatedToken(String token) {
        Long validateAt = Instant.now().toEpochMilli();
        int result = userTokenRepository.activateUserByToken(token, validateAt);
        return result == 1;
    }

    @Override
    @Transactional
    public void validateResetPasswordToken(String token) {
    UserToken userToken = userTokenRepository.findByToken(token).
            orElseThrow(()-> new ApplicationException(BusinessErrorCode.INVALID_TOKEN));
    userToken.setValidateAt(Instant.now().toEpochMilli());
    }

    @Override
    public Optional<UserToken> findById(Long id) {
        return Optional.empty();
    }

    @Override
    @Transactional
    public UserToken save(UserToken entity) {
        return userTokenRepository.save(entity);
    }

    @Override
    public UserToken update(UserToken entity) {
        return null;
    }

    @Override
    public void delete(List<Long> ids) {

    }
}
