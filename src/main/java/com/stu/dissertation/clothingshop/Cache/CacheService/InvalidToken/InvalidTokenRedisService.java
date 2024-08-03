package com.stu.dissertation.clothingshop.Cache.CacheService.InvalidToken;

public interface InvalidTokenRedisService {
    void addToken(String token,  Long expiration);
    boolean checkToken(String token);
}
