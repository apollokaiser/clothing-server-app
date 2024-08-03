package com.stu.dissertation.clothingshop.Cache.CacheService;

import java.util.List;
import java.util.Set;

public interface BaseRedisService {
    void set(String key, String value);
    Object get(String key);
    void hashSet(String key, String field, Object value);
    Object hashGet(String key, String field);
    void setTimeToLive(String key, long ttl);
    List<Object> hashGetByFieldPrefix(String key, String fieldPrefix);
    List<Object> getByKeyPrefix(String keyPrefix);
    List<String> scanKeys(String pattern);
    Set<String> getFieldPrefixes(String key);
    void delete(String key);
    void delete(List<String> keys);
    void delete(String key, String field);
    void delete(String key, List<String> fields);
}
