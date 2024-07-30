package com.stu.dissertation.clothingshop.Cache.CacheService.TheLoai;

import com.stu.dissertation.clothingshop.DTO.TheLoaiDTO;

import java.util.List;

public interface TheLoaiRedisService {
    List<TheLoaiDTO> getTheLoai();
    void updateTheLoai(List<TheLoaiDTO> theLoai);
    void clearTheLoai();
}
