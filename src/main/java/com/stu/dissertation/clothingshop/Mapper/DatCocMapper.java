package com.stu.dissertation.clothingshop.Mapper;

import com.stu.dissertation.clothingshop.DTO.DatCocDTO;
import com.stu.dissertation.clothingshop.Entities.DatCoc;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DatCocMapper {
    public static final DatCocMapper INSTANCE = Mappers.getMapper(DatCocMapper.class);
    DatCocDTO convert(DatCoc datCoc);
}
