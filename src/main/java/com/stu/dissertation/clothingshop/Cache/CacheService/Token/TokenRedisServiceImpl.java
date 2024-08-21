package com.stu.dissertation.clothingshop.Cache.CacheService.Token;

import com.stu.dissertation.clothingshop.Cache.CacheService.BaseRedisService;
import com.stu.dissertation.clothingshop.Enum.RedisKey;
import com.stu.dissertation.clothingshop.Utils.TimeBuilder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenRedisServiceImpl implements TokenRedisService {
    private final BaseRedisService baseRedisService;
    @NonFinal
    @Value("${application.security.verification_expired}")
    private Long verificationExpired;


    @Override
    public void saveInvalidToken(@NonNull String jwtID,
                         @NonNull Long expiration) {
        baseRedisService.set(jwtID, jwtID);
        long expSeconds = TimeBuilder.betweenNow(expiration);
        baseRedisService.setTimeToLive(jwtID, expSeconds);
    }

    @Override
    public boolean checkInvalidToken(String jwtID) {
        return baseRedisService.get(jwtID) != null;
    }
    @Override
    public void saveUserToken(String uid,String token) {
        String key = RedisKey.USER_TOKEN.getKey() + uid;
        baseRedisService.set(key, token);
        long expSeconds = verificationExpired * 60;
        baseRedisService.setTimeToLive(key, expSeconds);
    }
    @Override
    public boolean checkUserToken(String uid, String token) {
        String key = RedisKey.USER_TOKEN.getKey() + uid;
        return !baseRedisService.get(key).equals(token);
    }
}
