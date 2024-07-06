package com.stu.dissertation.clothingshop.Mapper;

import com.stu.dissertation.clothingshop.DTO.KhuyenMaiDTO;
import com.stu.dissertation.clothingshop.DTO.KhuyenMaiThanhToanDTO;
import com.stu.dissertation.clothingshop.DTO.KhuyenMaiTheLoaiDTO;
import com.stu.dissertation.clothingshop.Entities.KhuyenMai;
import com.stu.dissertation.clothingshop.Entities.TheLoai;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface KhuyenMaiMapper {
    KhuyenMaiMapper INSTANCE = Mappers.getMapper(KhuyenMaiMapper.class);
    KhuyenMaiDTO convert(KhuyenMai khuyenMai);
    KhuyenMaiThanhToanDTO convertToPromotionPayment(KhuyenMai khuyenMai);
    KhuyenMai convert(KhuyenMaiDTO khuyenMai);
    @Mapping(target = "theLoais", source = "theLoais", qualifiedByName = "getTheLoaiIds")
    KhuyenMaiTheLoaiDTO convertPromtionCategory(KhuyenMai khuyenMai);
    @Named("getTheLoaiIds")
    default List<Long> getTheLoaiIds(Set<TheLoai> theLoais){
        return theLoais.stream().map(TheLoai::getMaLoai).collect(Collectors.toList());
    }
}
