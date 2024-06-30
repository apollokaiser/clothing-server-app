package com.stu.dissertation.clothingshop.Mapper;

import com.stu.dissertation.clothingshop.DTO.ChiTietDonThueDTO;
import com.stu.dissertation.clothingshop.Entities.ChiTietDonThue;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ChiTietDonThueMapper {
    @Mapping(target = "trangPhuc", ignore = true)
    @Mapping(target = "donThue", ignore = true)
    @Mapping(target = "maChiTiet", ignore = true)
    ChiTietDonThue convert(ChiTietDonThueDTO chiTiet);
}
