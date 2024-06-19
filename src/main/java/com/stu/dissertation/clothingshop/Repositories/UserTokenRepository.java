package com.stu.dissertation.clothingshop.Repositories;

import com.stu.dissertation.clothingshop.Entities.UserToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserTokenRepository extends JpaRepository<UserToken, String> {
    @Query("SELECT t FROM UserToken t WHERE t.token = ?1")
    Optional<UserToken> findByToken(String token);
    @Procedure("PROC_activate_user_by_token")
    int activateUserByToken(
            @Param("user_token") String token,
            @Param("validate_at") Long validateAt);
}
