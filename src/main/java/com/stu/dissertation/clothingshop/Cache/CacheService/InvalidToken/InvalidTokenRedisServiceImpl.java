package com.stu.dissertation.clothingshop.Cache.CacheService.InvalidToken;

import com.stu.dissertation.clothingshop.Cache.CacheService.BaseRedisService;
import com.stu.dissertation.clothingshop.Utils.TimeBuilder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InvalidTokenRedisServiceImpl implements InvalidTokenRedisService {
    private final BaseRedisService baseRedisService;


    @Override
    public void addToken(@NonNull String jwtID,
                         @NonNull Long expiration) {
        baseRedisService.set(jwtID, jwtID);
        Long expSeconds = TimeBuilder.betweenNow(expiration);
        baseRedisService.setTimeToLive(jwtID, expSeconds);
    }

    @Override
    public boolean checkToken(String jwtID) {
        return baseRedisService.get(jwtID) != null;
    }
}
