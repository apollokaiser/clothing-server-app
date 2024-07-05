package com.stu.dissertation.clothingshop.Mapper;

import com.stu.dissertation.clothingshop.DTO.ChiTietDonThueDTO;
import com.stu.dissertation.clothingshop.DTO.TrangPhucPreviewDTO;
import com.stu.dissertation.clothingshop.Entities.ChiTietDonThue;
import com.stu.dissertation.clothingshop.Entities.TrangPhuc;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ChiTietDonThueMapper {
    ChiTietDonThueMapper INSTANCE = Mappers.getMapper(ChiTietDonThueMapper.class);
    @Mapping(target = "trangPhuc", ignore = true)
    @Mapping(target = "donThue", ignore = true)
    @Mapping(target = "maChiTiet", ignore = true)
    ChiTietDonThue convert(ChiTietDonThueDTO chiTiet);

    @Mapping(target="maTrangPhuc", source = "trangPhuc.id")
    @Mapping(target = "trangPhuc", source = "trangPhuc", qualifiedByName = "getTrangPhucInOrder")
    ChiTietDonThueDTO convert(ChiTietDonThue chitiet);

    @Named("getTrangPhucInOrder")
    default TrangPhucPreviewDTO getTrangPhucInOrder(TrangPhuc trangPhuc) {
       return TrangphucDetailMapper.INSTANCE.convertToPreview(trangPhuc);
    }
}
