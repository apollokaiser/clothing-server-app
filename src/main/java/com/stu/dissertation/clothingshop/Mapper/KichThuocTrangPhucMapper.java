package com.stu.dissertation.clothingshop.Mapper;

import com.stu.dissertation.clothingshop.DTO.KichThuocTrangPhucDTO;
import com.stu.dissertation.clothingshop.Entities.KichThuoc_TrangPhuc;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface KichThuocTrangPhucMapper {
    KichThuocTrangPhucMapper INSTANCE = Mappers.getMapper(KichThuocTrangPhucMapper.class);
    @Mapping(target = "maKichThuoc", source = "id.maKichThuoc")
    KichThuocTrangPhucDTO convert(KichThuoc_TrangPhuc kichThuoc);
}
