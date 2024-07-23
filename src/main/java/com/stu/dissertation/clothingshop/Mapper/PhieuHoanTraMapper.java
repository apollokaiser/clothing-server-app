package com.stu.dissertation.clothingshop.Mapper;

import com.stu.dissertation.clothingshop.DTO.PhieuHoanTraDTO;
import com.stu.dissertation.clothingshop.Entities.PhieuHoanTra;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PhieuHoanTraMapper {
    PhieuHoanTraMapper INSTANCE = Mappers.getMapper(PhieuHoanTraMapper.class);
    @Mapping(target="donThue", ignore = true)
    @Mapping(target="ngayTra", ignore = true)
    @Mapping(target="id", ignore = true)
    PhieuHoanTra convert(PhieuHoanTraDTO phieuHoanTra);
}
