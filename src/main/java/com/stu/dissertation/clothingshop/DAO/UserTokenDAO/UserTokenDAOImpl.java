package com.stu.dissertation.clothingshop.DAO.UserTokenDAO;

import com.stu.dissertation.clothingshop.Entities.UserToken;
import com.stu.dissertation.clothingshop.Repositories.UserTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserTokenDAOImpl implements UserTokenDAO{
    private final UserTokenRepository userTokenRepository;

    @Override
    public  Optional<UserToken> findByToken(String token) {
        return userTokenRepository.findByToken(token);
    }

    @Override
    public boolean validatedToken(String token) {
        int result = userTokenRepository.activateUserByToken(token);
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
    public UserToken save(UserToken entity) {
        return null;
    }

    @Override
    public UserToken update(UserToken entity) {
        return null;
    }

    @Override
    public void delete(List<Long> ids) {

    }
}
