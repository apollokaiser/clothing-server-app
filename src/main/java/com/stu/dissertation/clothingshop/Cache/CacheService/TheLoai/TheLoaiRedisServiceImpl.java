package com.stu.dissertation.clothingshop.Cache.CacheService.TheLoai;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stu.dissertation.clothingshop.Cache.CacheService.BaseRedisService;
import com.stu.dissertation.clothingshop.DTO.TheLoaiDTO;
import com.stu.dissertation.clothingshop.Enum.BusinessErrorCode;
import com.stu.dissertation.clothingshop.Enum.RedisKey;
import com.stu.dissertation.clothingshop.Exception.CustomException.ApplicationException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TheLoaiRedisServiceImpl implements TheLoaiRedisService{
    private final BaseRedisService baseService;
    private final ObjectMapper objectMapper;
    @Value("${application.redis.time-to-live.categories}")
    private long cartTTL;
    @Override
    public List<TheLoaiDTO> getTheLoai() {
       try {
           String categories = (String) baseService.get(RedisKey.CATEGORY.getKey());
           if (categories == null) {
              return null;
           }
           return objectMapper.readValue(categories, new TypeReference<>() {
           });
       } catch (Exception e) {
           System.out.println("Category cache error: " + e.getMessage());
          return null;
       }
    }

    @Override
    @Async
    public void updateTheLoai(List<TheLoaiDTO> theLoai) {
        try {
            String theLoaiJSON = objectMapper.writeValueAsString(theLoai);
            baseService.set(RedisKey.CATEGORY.getKey(), theLoaiJSON);
            baseService.setTimeToLive(RedisKey.CATEGORY.getKey(), cartTTL * 24 * 60 * 60);
        } catch (Exception e) {
            System.out.println("Not found your data");
        }
    }

    @Override
    public void clearTheLoai() {
        baseService.delete(RedisKey.CATEGORY.getKey());
    }
}
