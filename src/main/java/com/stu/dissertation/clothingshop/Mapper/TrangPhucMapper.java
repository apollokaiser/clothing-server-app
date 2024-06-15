package com.stu.dissertation.clothingshop.Mapper;

import com.stu.dissertation.clothingshop.DTO.TrangPhucDTO;
import com.stu.dissertation.clothingshop.Entities.HinhAnhTrangPhuc;
import com.stu.dissertation.clothingshop.Entities.TrangPhuc;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface TrangPhucMapper {
    @Mapping(target="hinhAnhs", source = "hinhAnhs", qualifiedByName = "hinhAnhsToString")
    TrangPhucDTO convert(TrangPhuc trangPhuc);
    @Mapping(target="hinhAnhs", source = "hinhAnhs", qualifiedByName = "StringToHinhAnhs")
    TrangPhuc convert(TrangPhucDTO trangPhuc);

    @Named("hinhAnhsToString")
    default List<String> hinhAnhsToString(Set<HinhAnhTrangPhuc> hinhAnhs){
        List<String> hinhs = hinhAnhs.stream().map(HinhAnhTrangPhuc::getHinhAnh).collect(Collectors.toList());
        return hinhs;
    }
    @Named("StringToHinhAnhs")
    default Set<HinhAnhTrangPhuc> StringToHinhAnhs(List<String> hinhAnhs){
        Set<HinhAnhTrangPhuc> hinhs = hinhAnhs.stream().map(HinhAnhTrangPhuc::new).collect(Collectors.toSet());
        return hinhs;
    }
}
