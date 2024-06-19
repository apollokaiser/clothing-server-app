package com.stu.dissertation.clothingshop.Mapper;

import com.stu.dissertation.clothingshop.DTO.TaiKhoanDTO;
import com.stu.dissertation.clothingshop.Entities.TaiKhoanLienKet;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TaiKhoanMapper {
    TaiKhoanMapper INSTANCE = Mappers.getMapper(TaiKhoanMapper.class);
    TaiKhoanDTO convert(TaiKhoanLienKet taiKhoanLienKet);
}

