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
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "donThue", ignore = true)
    @Mapping(target="outfitSize", ignore = true)
    @Mapping(target="trangPhucChinh", ignore = true)
    ChiTietDonThue convert(ChiTietDonThueDTO chiTiet);

    @Mapping(target="outfitSizeId", source = "id.outfitSizeId")
    @Mapping(target = "trangPhuc", source = "outfitSize.trangPhuc", qualifiedByName = "getTrangPhucInOrder")
    ChiTietDonThueDTO convert(ChiTietDonThue chitiet);

    @Named("getTrangPhucInOrder")
    default TrangPhucPreviewDTO getTrangPhucInOrder(TrangPhuc trangPhuc) {
       return TrangphucDetailMapper.INSTANCE.convertToPreview(trangPhuc);
    }
}
