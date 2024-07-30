package com.stu.dissertation.clothingshop.Service.InvalidToken;

import com.stu.dissertation.clothingshop.Entities.InvalidToken;
import com.stu.dissertation.clothingshop.Repositories.InvalidTokenRepository;
import com.stu.dissertation.clothingshop.Security.JWTAuth.JWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InvalidTokenService {
    private final InvalidTokenRepository invalidTokenRepository;
    private final JWTService jwtService;
    public InvalidToken save(String token) {
        Long exp = jwtService.extractExpTime(token);
        InvalidToken invalidToken = new InvalidToken(token, exp);
       return invalidTokenRepository.save(invalidToken);
    }
}
