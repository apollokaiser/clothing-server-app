package com.stu.dissertation.clothingshop.Mapper;

import com.stu.dissertation.clothingshop.DTO.GioHangDTO;
import com.stu.dissertation.clothingshop.Entities.HinhAnhTrangPhuc;
import com.stu.dissertation.clothingshop.Entities.NguoiDung_GioHang;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface GioHangMapper {
    @Mapping(target = "id", source = "maTrangPhuc")
    @Mapping(target="quantity", source = "soLuong")
    @Mapping(target="size", source = "kichThuoc")
    @Mapping(target="full", source = "toanPhan")
    GioHangDTO convert(NguoiDung_GioHang gioHang);
}