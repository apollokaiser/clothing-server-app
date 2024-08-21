package com.stu.dissertation.clothingshop.Repositories;

import com.stu.dissertation.clothingshop.Entities.InvalidToken;
import org.springframework.data.jpa.repository.JpaRepository;

@Deprecated(forRemoval = true)
public interface InvalidTokenRepository extends JpaRepository<InvalidToken, String> {
}
