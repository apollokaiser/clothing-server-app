package com.stu.dissertation.clothingshop.Cache.CacheService.GioHang;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stu.dissertation.clothingshop.Cache.CacheService.BaseRedisService;
import com.stu.dissertation.clothingshop.DTO.GioHangDTO;
import com.stu.dissertation.clothingshop.Enum.RedisKey;
import com.stu.dissertation.clothingshop.Enum.RedisPrefix;
import com.stu.dissertation.clothingshop.Payload.Request.Cart;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class GioHangRedisServiceImpl implements GioHangRedisService{
    private final BaseRedisService redisService;
    private final ObjectMapper redistObjectMapper;

    @Value("${application.redis.time-to-live.cart}")
    private long cartTTL;

    private String getFieldPrefix(GioHangDTO gioHangDTO) {
        return String.format("%s%s:%s", RedisPrefix.CART_PREFIX.getPrefix(), gioHangDTO.getId(), gioHangDTO.getSize());
    }
    @Override
    public void saveCart(String id, GioHangDTO gioHangDTO)  {
        log.info("Saving cart {}", id);
        String key = RedisKey.CART.getKey() + id;
        redisService.hashSet(key, getFieldPrefix(gioHangDTO), gioHangDTO);
        redisService.setTimeToLive(key, getCartTTL());
    }
    private long getCartTTL() {
        return cartTTL * 24 * 60 * 60;
    }
    @Override
    public void deleteCart(String id, GioHangDTO gioHangDTO) {
        log.info("Deleting cart {}", id);
        String key = RedisKey.CART.getKey() + id;
        redisService.delete(key, getFieldPrefix(gioHangDTO));
    }

    @Override
    public void deleteCarts(String id, List<GioHangDTO> gioHangDTOs) {
        log.info("begin delete carts");
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
    public void savePreOrder(String id, Cart cart) {
        log.info(("Begin save pre order"));
        try {
        String cartJSON = redistObjectMapper.writeValueAsString(cart);
        redisService.set(RedisKey.PRE_ORDER.getKey() + id, cartJSON);
        redisService.setTimeToLive(RedisKey.PRE_ORDER.getKey() + id, 10L * 60);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Cart getPreOrder(String id) {
        log.info(("Begin get pre order"));
        String key = RedisKey.PRE_ORDER.getKey() + id;
        String cartJSON = (String) redisService.get(key);
        if(cartJSON == null) return null;
        try {
            return redistObjectMapper.readValue(cartJSON, Cart.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public void clearPreOrder(String id) {
        log.info(("Begin clear pre order"));
        String key = RedisKey.PRE_ORDER.getKey() + id;
        redisService.delete(key);
    }

    @Override
    public List<GioHangDTO> getCart(String id) {
        String key = RedisKey.CART.getKey() + id;
        List<Object> obj = redisService.hashGetByFieldPrefix(key, RedisPrefix.CART_PREFIX.getPrefix());
        List<GioHangDTO> carts = new ArrayList<>();
        for (Object item : obj) {
            GioHangDTO gioHangDTO = redistObjectMapper.convertValue(item, GioHangDTO.class);
            carts.add(gioHangDTO);
        }
        return carts;
    }

}
