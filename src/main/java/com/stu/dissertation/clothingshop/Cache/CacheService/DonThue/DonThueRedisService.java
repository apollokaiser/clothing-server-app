package com.stu.dissertation.clothingshop.Cache.CacheService.DonThue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.stu.dissertation.clothingshop.Payload.Request.OrderRequest;

public interface DonThueRedisService {
    void saveDonThue(OrderRequest order, String orderId, String uid) throws JsonProcessingException;
    OrderRequest getDonThue(String orderId,String uid, boolean clear);
    void clearDonthue(String orderId,String uid);
}
