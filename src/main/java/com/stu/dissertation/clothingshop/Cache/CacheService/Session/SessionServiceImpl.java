package com.stu.dissertation.clothingshop.Cache.CacheService.Session;


import com.stu.dissertation.clothingshop.Cache.CacheService.BaseRedisService;
import com.stu.dissertation.clothingshop.Enum.RedisKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class SessionServiceImpl implements SessionService {
    private final BaseRedisService redisService;
    @Value("${application.redis.time-to-live.session}")
    private Long sessionExpiration;

    public SessionServiceImpl(BaseRedisService redisService) {
        this.redisService = redisService;
    }

    @Override
    public String getSession(String session) {
        if(session==null) return null;
        return (String) redisService.get(session);
    }
    @Override
    public void setSession(String session) {
        long createAt = Instant.now().getEpochSecond();
        long sessionExp = Instant.now().plus(sessionExpiration, ChronoUnit.MINUTES).getEpochSecond();
        String prefix = RedisKey.SESIONS.getKey();
        redisService.set(prefix + session, Long.toString(createAt));
        redisService.setTimeToLive(prefix + session, sessionExp);
    }
}
