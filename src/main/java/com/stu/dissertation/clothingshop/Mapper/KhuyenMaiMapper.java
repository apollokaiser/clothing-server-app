package com.stu.dissertation.clothingshop.Mapper;

import com.stu.dissertation.clothingshop.DTO.KhuyenMaiDTO;
import com.stu.dissertation.clothingshop.DTO.KhuyenMaiThanhToanDTO;
import com.stu.dissertation.clothingshop.Entities.KhuyenMai;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface KhuyenMaiMapper {
    KhuyenMaiMapper INSTANCE = Mappers.getMapper(KhuyenMaiMapper.class);
    KhuyenMaiDTO convert(KhuyenMai khuyenMai);
    KhuyenMaiThanhToanDTO convertToPromotionPayment(KhuyenMai khuyenMai);
    KhuyenMai convert(KhuyenMaiDTO khuyenMai);
}
