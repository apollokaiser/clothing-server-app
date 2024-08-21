package com.stu.dissertation.clothingshop.Cache.CacheService.Token;

public interface TokenRedisService {
    void saveInvalidToken(String token,  Long expiration);
    boolean checkInvalidToken(String token);
    default void saveUserToken(String uid, String token) {
        System.out.println("SaveUserToken");
    }
    default boolean checkUserToken(String uid, String token) {
        System.out.println("CheckUserToken");
        return true;
    }
}
