package com.stu.dissertation.clothingshop.Mapper;

import com.stu.dissertation.clothingshop.DTO.KichThuocTrangPhucDTO;
import com.stu.dissertation.clothingshop.DTO.OutfitCartDTO;
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
    @Mapping(target = "manhTrangPhucs", source = "manhTrangPhucs", qualifiedByName = "mapManhTrangPhuc")
    @Mapping(target = "kichThuocs", source = "kichThuocTrangPhucs", qualifiedByName = "convertKichThuocToDTO")
    TrangPhucDetailDTO convert (TrangPhuc trangPhuc);

    @Mapping(target = "manhTrangPhucs", source = "manhTrangPhucs", qualifiedByName = "getManhTrangPhucDTO")
    @Mapping(target = "theLoai", source = "theLoai.maLoai")
    @Mapping(target = "hinhAnh", source = "hinhAnhs", qualifiedByName = "getHinhAnhDTO")
    @Mapping(target = "kichThuocs", source = "kichThuocTrangPhucs", qualifiedByName = "convertKichThuocToDTO")
    OutfitCartDTO convertToCartItem(TrangPhuc trangPhuc);
    @Mapping(target="trangPhucChinh", source = "trangPhucChinh", qualifiedByName = "convertTrangPhucChinh")
    TrangPhucPreviewDTO convertToPreview(TrangPhuc trangPhuc);

    @Named("hinhAnhsToString")
    default Set<String> hinhAnhsToString(Set<HinhAnhTrangPhuc> hinhAnhs) {
        return hinhAnhs.stream()
                .map(HinhAnhTrangPhuc::getHinhAnh)
                .collect(Collectors.toSet());
    }
    @Named("mapManhTrangPhuc")
    default Set<TrangPhucDetailDTO> mapManhTrangPhuc(Set<TrangPhuc> mapManhTrangPhuc) {
        return mapManhTrangPhuc.stream()
                .map(this::convert)
                .collect(Collectors.toSet());
    }
    @Named("convertKichThuocToDTO")
    default Set<KichThuocTrangPhucDTO> convertKichThuocToDTO(Set<KichThuoc_TrangPhuc> kichThuocs){
        return kichThuocs.stream().map(KichThuocTrangPhucMapper.INSTANCE::convert)
                .collect(Collectors.toSet());
    }
    @Named("getManhTrangPhucDTO")
    default Set<OutfitCartDTO> getManhTrangPhucDTO(Set<TrangPhuc> manhTrangPhucs){
        return manhTrangPhucs.stream().map(this::convertToCartItem)
               .collect(Collectors.toSet());
    }
    @Named("getHinhAnhDTO")
    default String getHinhAnhDTO(Set<HinhAnhTrangPhuc> hinhAnhs){
        if(hinhAnhs.isEmpty()) return null;
        return hinhAnhs.stream().findFirst().orElseGet(() -> null).getHinhAnh();
    }
    @Named("convertTrangPhucChinh")
    default TrangPhucPreviewDTO convertTrangPhucChinh(TrangPhuc trangPhuc) {
        return this.convertToPreview(trangPhuc);
    }
}
