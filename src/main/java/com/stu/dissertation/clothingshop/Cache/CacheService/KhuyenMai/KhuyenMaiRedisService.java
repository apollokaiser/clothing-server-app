package com.stu.dissertation.clothingshop.Cache.CacheService.KhuyenMai;

import com.stu.dissertation.clothingshop.DTO.KhuyenMaiTheLoaiDTO;

import java.util.List;

public interface KhuyenMaiRedisService {
    void addCategoryPromotions(List<KhuyenMaiTheLoaiDTO> promotions);
    List<KhuyenMaiTheLoaiDTO> getCategoryPromotions();
    void clearCategoryPromotions();
}
