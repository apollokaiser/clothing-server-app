package com.stu.dissertation.clothingshop.Mapper;

import com.stu.dissertation.clothingshop.DTO.KichThuocTrangPhucDTO;
import com.stu.dissertation.clothingshop.DTO.TrangPhucDTO;
import com.stu.dissertation.clothingshop.DTO.UpdateTrangPhucDTO;
import com.stu.dissertation.clothingshop.Entities.ChiTietDonThue;
import com.stu.dissertation.clothingshop.Entities.HinhAnhTrangPhuc;
import com.stu.dissertation.clothingshop.Entities.KichThuoc_TrangPhuc;
import com.stu.dissertation.clothingshop.Entities.TrangPhuc;
import org.mapstruct.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface TrangPhucMapper {
    @Mapping(target="hinhAnhs", source = "hinhAnhs", qualifiedByName = "hinhAnhsToString")
    @Mapping(target = "hasOrderCount", source = "chiTietDonThues", qualifiedByName = "hasOrderCount")
    TrangPhucDTO convert(TrangPhuc trangPhuc);
    @Mapping(target="hinhAnhs", source = "hinhAnhs", qualifiedByName = "StringToHinhAnhs")
    TrangPhuc convert(TrangPhucDTO trangPhuc);
    @Mapping(target="id", ignore = true)
    @Mapping(target="theLoai", ignore = true)
    @Mapping(target="trangPhucChinhs", ignore = true)
    @Mapping(target="chiTietDonThues", ignore = true)
    @Mapping(target="kichThuocTrangPhucs", ignore = true)
    @Mapping(target = "gioHangs", ignore = true)
    @Mapping(target = "phuKiens", ignore = true)
    @Mapping(target = "hinhAnhs", source = "hinhAnhs", qualifiedByName = "setHinhAnhs")
    TrangPhuc convert(UpdateTrangPhucDTO trangPhuc);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target="id", ignore = true)
    @Mapping(target = "theLoai", ignore = true)
    @Mapping(target = "hinhAnhs", ignore = true)
    @Mapping(target = "trangPhucChinhs", ignore = true)
    @Mapping(target = "phuKiens", ignore = true)
    @Mapping(target = "gioHangs", ignore = true)
    @Mapping(target = "kichThuocTrangPhucs", ignore = true)
    @Mapping(target = "chiTietDonThues", ignore = true)
    void updateEntityFromDto(UpdateTrangPhucDTO trangPhucDTO, @MappingTarget TrangPhuc trangPhuc);
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
    @Named("setHinhAnhs")
    default Set<HinhAnhTrangPhuc> setHinhAnhs(List<String> hinhAnhs){
      Set<HinhAnhTrangPhuc> images =  hinhAnhs.stream().map(hinh -> HinhAnhTrangPhuc.builder()
              .hinhAnh(hinh)
              .build()).collect(Collectors.toSet());
      return images;
    }
    @Named("hasOrderCount")
    default int hasOrderCount(Set<ChiTietDonThue> chiTietDonThues){
        return chiTietDonThues.size();
    }
    @Mapping(target = "theLoai", source = "theLoai.maLoai" )
    @Mapping(target="hinhAnhs", source = "hinhAnhs", qualifiedByName = "hinhAnhsToString")
    @Mapping(target = "kichThuocs", source = "kichThuocTrangPhucs", qualifiedByName = "convertKichThuocToDTO")
    UpdateTrangPhucDTO converToUpdateDTO(TrangPhuc trangPhuc);

    @Named("convertKichThuocToDTO")
    default List<KichThuocTrangPhucDTO> convertKichThuocToDTO (Set<KichThuoc_TrangPhuc> kichThuocs){
            return kichThuocs.stream().map(KichThuocTrangPhucMapper.INSTANCE::convert)
                    .collect(Collectors.toList());
    }
}
