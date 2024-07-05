package com.stu.dissertation.clothingshop.Mapper;

import com.stu.dissertation.clothingshop.DTO.KichThuocTrangPhucDTO;
import com.stu.dissertation.clothingshop.DTO.TrangPhucDetailDTO;
import com.stu.dissertation.clothingshop.DTO.TrangPhucPreviewDTO;
import com.stu.dissertation.clothingshop.Entities.HinhAnhTrangPhuc;
import com.stu.dissertation.clothingshop.Entities.KichThuoc_TrangPhuc;
import com.stu.dissertation.clothingshop.Entities.TrangPhuc;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface TrangphucDetailMapper {
    TrangphucDetailMapper INSTANCE = Mappers.getMapper(TrangphucDetailMapper.class);
    @Mapping(source = "theLoai.maLoai", target = "theLoai")
    @Mapping(source = "hinhAnhs", target = "hinhAnhs", qualifiedByName = "hinhAnhsToString")
    @Mapping(target = "phuKiens", source = "phuKiens", qualifiedByName = "mapPhuKien")
    @Mapping(target = "kichThuocs", source = "kichThuocTrangPhucs", qualifiedByName = "convertKichThuocToDTO")
    TrangPhucDetailDTO convert (TrangPhuc trangPhuc);

    @Mapping(target = "phuKiens", ignore = true)
    @Mapping(target = "theLoai", source = "theLoai.maLoai")
    @Mapping(target = "moTa", ignore = true)
    @Mapping(source = "hinhAnhs", target = "hinhAnhs", qualifiedByName = "hinhAnhsToString")
    @Mapping(target = "kichThuocs", source = "kichThuocTrangPhucs", qualifiedByName = "convertKichThuocToDTO")
    TrangPhucDetailDTO convertToCartItem(TrangPhuc trangPhuc);

    TrangPhucPreviewDTO convertToPreview(TrangPhuc trangPhuc);

    @Named("hinhAnhsToString")
    default Set<String> hinhAnhsToString(Set<HinhAnhTrangPhuc> hinhAnhs) {
        return hinhAnhs.stream()
                .map(HinhAnhTrangPhuc::getHinhAnh)
                .collect(Collectors.toSet());
    }
    @Named("mapPhuKien")
    default Set<TrangPhucDetailDTO> mapPhuKien(Set<TrangPhuc> phuKiens) {
        return phuKiens.stream()
                .map(this::convert)
                .collect(Collectors.toSet());
    }
    @Named("convertKichThuocToDTO")
    default Set<KichThuocTrangPhucDTO> convertKichThuocToDTO(Set<KichThuoc_TrangPhuc> kichThuocs){
        return kichThuocs.stream().map(KichThuocTrangPhucMapper.INSTANCE::convert)
                .collect(Collectors.toSet());
    }
}
