package com.stu.dissertation.clothingshop.Mapper;

import com.stu.dissertation.clothingshop.DTO.KhuyenMaiDTO;
import com.stu.dissertation.clothingshop.DTO.TheLoaiDTO;
import com.stu.dissertation.clothingshop.DTO.TheLoaiPromotionDTO;
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
public interface TheLoaiMapper {
    TheLoaiMapper INSTANCE = Mappers.getMapper(TheLoaiMapper.class);

    @Mapping(target = "khuyenMai", source = "khuyenMais", qualifiedByName = "checkKhuyenMai")
    @Mapping(target="children", source="children", qualifiedByName = "mapChildren")
    TheLoaiDTO convert(TheLoai theLoai);
    @Mapping(target = "khuyenMai", source = "khuyenMais", qualifiedByName = "getKhuyenMai")
    TheLoaiPromotionDTO convertToGetPromotion(TheLoai theLoai);

    @Named("getKhuyenMai")
        default KhuyenMaiDTO getKhuyenMai(Set<KhuyenMai> khuyenMai) {
            return khuyenMai.stream()
                   .map(KhuyenMaiMapper.INSTANCE::convert)
                   .findFirst()
                   .orElse(null);
        }
    @Named("checkKhuyenMai")
    default boolean checkKhuyenMai(Set<KhuyenMai> khuyenMais){
        return !khuyenMais.isEmpty();
    }
    @Named("mapChildren")
    default List<TheLoaiDTO> mapChildren(List<TheLoai> children){
        if(children.isEmpty()) return null;
        return children.stream()
                .map(this::convert).collect(Collectors.toList());
    }
}
