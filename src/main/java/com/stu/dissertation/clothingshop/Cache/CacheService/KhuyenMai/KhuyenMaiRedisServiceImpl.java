package com.stu.dissertation.clothingshop.Cache.CacheService.KhuyenMai;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stu.dissertation.clothingshop.Cache.CacheService.BaseRedisService;
import com.stu.dissertation.clothingshop.DTO.KhuyenMaiTheLoaiDTO;
import com.stu.dissertation.clothingshop.Enum.BusinessErrorCode;
import com.stu.dissertation.clothingshop.Enum.RedisKey;
import com.stu.dissertation.clothingshop.Exception.CustomException.ApplicationException;
import com.stu.dissertation.clothingshop.Utils.TimeBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class KhuyenMaiRedisServiceImpl implements KhuyenMaiRedisService{
    private final BaseRedisService baseRedisService;
    private final ObjectMapper redistObjectMapper;

    private String getKey(Long id) {
        return RedisKey.CATEGORY_PROMOTION.getKey() +id;
    }
    @Override
    public void addCategoryPromotions(List<KhuyenMaiTheLoaiDTO> promotions) {
        try {
            for(KhuyenMaiTheLoaiDTO promotion : promotions) {
                String promotionJSON = redistObjectMapper.writeValueAsString(promotion);
                String key = getKey(promotion.getMaKhuyenMai());
                baseRedisService.set(key, promotionJSON);
                long TTL = TimeBuilder.betweenNow(promotion.getNgayKetThuc());
                baseRedisService.setTimeToLive(RedisKey.CATEGORY_PROMOTION.getKey() + promotion.getMaKhuyenMai(), TTL);
            }
        }catch (JsonProcessingException e) {
            throw new ApplicationException(BusinessErrorCode.NOT_ALLOW_DATA_SOURCE);
        }
    }

    @Override
    public List<KhuyenMaiTheLoaiDTO> getCategoryPromotions() {
        List<Object> cobj = baseRedisService.getByKeyPrefix(RedisKey.CATEGORY_PROMOTION.getKey());
        if(cobj==null) return null;
        List<KhuyenMaiTheLoaiDTO> categoryPromotions = new ArrayList<>();
        for(Object item: cobj) {
            String promotionJSON = (String) item;
            try {
                KhuyenMaiTheLoaiDTO promotion = redistObjectMapper.readValue(promotionJSON, KhuyenMaiTheLoaiDTO.class);
                categoryPromotions.add(promotion);
            } catch (JsonProcessingException e) {
                throw new ApplicationException(BusinessErrorCode.NOT_ALLOW_DATA_SOURCE);
            }
        }
        return categoryPromotions;
    }

    @Override
    public void clearCategoryPromotions() {
    List<String> keys = baseRedisService.scanKeys(RedisKey.CATEGORY_PROMOTION.getKey());
    baseRedisService.delete(keys);
    }
}
