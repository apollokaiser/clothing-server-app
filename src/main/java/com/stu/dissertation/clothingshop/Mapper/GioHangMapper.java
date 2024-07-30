package com.stu.dissertation.clothingshop.Mapper;

import com.stu.dissertation.clothingshop.DTO.GioHangDTO;
import com.stu.dissertation.clothingshop.Entities.NguoiDung_GioHang;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface GioHangMapper {
    @Mapping(target = "id", source = "outfitSize.trangPhuc.id")
    @Mapping(target="quantity", source = "soLuong")
    @Mapping(target="size", source = "outfitSize.kichThuoc.id")
    @Mapping(target="parentId", source = "trangPhucChinh.id")
    GioHangDTO convert(NguoiDung_GioHang gioHang);
}
