package com.stu.dissertation.clothingshop.Cache.CacheService.GioHang;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stu.dissertation.clothingshop.Cache.CacheService.BaseRedisService;
import com.stu.dissertation.clothingshop.DTO.GioHangDTO;
import com.stu.dissertation.clothingshop.Enum.RedisKey;
import com.stu.dissertation.clothingshop.Enum.RedisPrefix;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GioHangRedisServiceImpl implements GioHangRedisService{
    private final BaseRedisService redisService;
    private final ObjectMapper objectMapper;

    @Value("${application.redis.time-to-live.cart}")
    private long cartTTL;

    private String getFieldPrefix(GioHangDTO gioHangDTO) {
        return String.format("%s%s:%s", RedisPrefix.CART_PREFIX.getPrefix(), gioHangDTO.getId(), gioHangDTO.getSize());
    }
    @Override
    public void saveCart(String id, GioHangDTO gioHangDTO)  {
        String key = RedisKey.CART.getKey() + id;
        redisService.hashSet(key, getFieldPrefix(gioHangDTO), gioHangDTO);
        redisService.setTimeToLive(key, getCartTTL());
    }
    private long getCartTTL() {
        return Instant.now().plus( cartTTL, ChronoUnit.DAYS).getEpochSecond();
    }
    @Override
    public void deleteCart(String id, GioHangDTO gioHangDTO) {
        String key = RedisKey.CART.getKey() + id;
        redisService.delete(key, getFieldPrefix(gioHangDTO));
    }

    @Override
    public void deleteCarts(String id, List<GioHangDTO> gioHangDTOs) {
        for(GioHangDTO dto : gioHangDTOs) {
            deleteCart(id, dto);
        }
    }

    @Override
    public void updateCart(String id, GioHangDTO gioHangDTO) {
        String key = RedisKey.CART.getKey() + id;
        redisService.hashSet(key, getFieldPrefix(gioHangDTO), gioHangDTO);
    }

    @Override
    public void updateCart(String id, GioHangDTO oldItem, GioHangDTO newItem) throws ParseException {
    deleteCart(id, oldItem);
    saveCart(id, newItem);
    }

    @Override
    public void clearCart(String id) {
        String key = RedisKey.CART.getKey() + id;
        redisService.delete(key);
    }

    @Override
    public List<GioHangDTO> getCart(String id) {
        String key = RedisKey.CART.getKey() + id;
        List<Object> obj = redisService.hashGetByFieldPrefix(key, RedisPrefix.CART_PREFIX.getPrefix());
        List<GioHangDTO> carts = new ArrayList<>();
        for (Object item : obj) {
            GioHangDTO gioHangDTO = objectMapper.convertValue(item, GioHangDTO.class);
            carts.add(gioHangDTO);
        }
        return carts;
    }

}
