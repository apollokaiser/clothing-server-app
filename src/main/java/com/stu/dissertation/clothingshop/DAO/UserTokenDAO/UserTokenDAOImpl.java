package com.stu.dissertation.clothingshop.DAO.UserTokenDAO;

import com.stu.dissertation.clothingshop.Entities.NguoiDung;
import com.stu.dissertation.clothingshop.Entities.UserToken;
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
    public void validateResetPasswordToken(String email, String token) {

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
