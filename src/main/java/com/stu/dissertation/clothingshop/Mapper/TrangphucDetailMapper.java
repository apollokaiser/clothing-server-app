package com.stu.dissertation.clothingshop.Mapper;

import com.stu.dissertation.clothingshop.DTO.TrangPhucDetailDTO;
import com.stu.dissertation.clothingshop.Entities.HinhAnhTrangPhuc;
import com.stu.dissertation.clothingshop.Entities.TrangPhuc;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface TrangphucDetailMapper {
    @Mapping(source = "theLoai.maLoai", target = "theLoai")
    @Mapping(source = "hinhAnhs", target = "hinhAnhs", qualifiedByName = "hinhAnhsToString")
    @Mapping(target = "phuKiens", source = "phuKiens", qualifiedByName = "mapPhuKien")
    @Mapping(target="kichThuocs", source="kichThuocs")
    TrangPhucDetailDTO convert (TrangPhuc trangPhuc);

    @Named("hinhAnhsToString")
    default Set<String> hinhAnhsToString(Set<HinhAnhTrangPhuc> hinhAnhs) {
        return hinhAnhs.stream()
                .map(HinhAnhTrangPhuc::getHinhAnh)
                .collect(Collectors.toSet());
    }
    @Named("mapPhuKien")
    default Set<TrangPhucDetailDTO> mapPhuKien(Set<TrangPhuc> phuKien) {
        return phuKien.stream()
                .map(this::convert)
                .collect(Collectors.toSet());
    }
}
