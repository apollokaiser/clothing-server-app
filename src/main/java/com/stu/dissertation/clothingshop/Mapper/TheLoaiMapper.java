package com.stu.dissertation.clothingshop.Mapper;

import com.stu.dissertation.clothingshop.DTO.*;
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

    @Mapping(target="children", source="children", qualifiedByName = "mapChildren")
    @Mapping(target="parentId", source="parent", qualifiedByName = "mapParent")
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
    @Named("mapChildren")
    default List<TheLoaiDTO> mapChildren(List<TheLoai> children){
        if( children == null || children.isEmpty()) return null;
        return children.stream()
                .map(this::convert).collect(Collectors.toList());
    }
    @Named("mapParent")
    default Long mapParent(TheLoai parent) {
        if(parent==null) return null;
        return parent.getMaLoai();
    }
}
