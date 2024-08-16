package com.stu.dissertation.clothingshop.Cache.CacheService.DonThue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stu.dissertation.clothingshop.Cache.CacheService.BaseRedisService;
import com.stu.dissertation.clothingshop.DTO.DonThueDTO;
import com.stu.dissertation.clothingshop.Enum.RedisKey;
import com.stu.dissertation.clothingshop.Payload.Request.OrderRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DonThueRedisServiceImpl implements DonThueRedisService{
    private final BaseRedisService baseRedisService;
    private final ObjectMapper redistObjectMapper;
    @Value("${application.redis.time-to-live.order}")
    private long orderTTL;
    private String getKey(String uid) {
        return RedisKey.ORDER.getKey() + uid;
    }
    @Override
    public void saveDonThue(OrderRequest order,String orderId, String uid) throws JsonProcessingException {
        String orderJSON = redistObjectMapper.writeValueAsString(order.getOrder());
        String key = getKey(uid);
        baseRedisService.hashSet(key,orderId,orderJSON);
        long ttl = orderTTL * 60;
        baseRedisService.setTimeToLive(getKey(uid),ttl);
    }

    @Override
    public OrderRequest getDonThue(String orderId, String uid, boolean clear) {
        String key = getKey(uid);
        Object orderJSON = baseRedisService.hashGet(key, orderId);
        if(orderJSON ==null) return null;
        try {
            if(clear) {
                baseRedisService.delete(getKey(uid), orderId);
            }
            DonThueDTO dto = redistObjectMapper.readValue((String) orderJSON, DonThueDTO.class);
            return OrderRequest.builder()
                    .order(dto).build();
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public void clearDonthue(String orderId, String uid) {
        baseRedisService.delete(getKey(uid), orderId);
    }

}
