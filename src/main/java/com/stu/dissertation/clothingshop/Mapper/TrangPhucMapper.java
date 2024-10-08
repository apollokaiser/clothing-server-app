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
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface TrangPhucMapper {
    @Mapping(target="hinhAnhs", source = "hinhAnhs", qualifiedByName = "hinhAnhsToString")
    @Mapping(target = "soLuong", source = "kichThuocTrangPhucs", qualifiedByName = "getQuantity")
    @Mapping(target = "hasOrderCount", source = "chiTietDonThues", qualifiedByName = "hasOrderCount")
    TrangPhucDTO convert(TrangPhuc trangPhuc);
    @Mapping(target="hinhAnhs", source = "hinhAnhs", qualifiedByName = "StringToHinhAnhs")
    TrangPhuc convert(TrangPhucDTO trangPhuc);
    @Mapping(target="id", ignore = true)
    @Mapping(target="theLoai", ignore = true)
    @Mapping(target="trangPhucChinh", ignore = true)
    @Mapping(target="chiTietDonThues", ignore = true)
    @Mapping(target="kichThuocTrangPhucs", ignore = true)
    @Mapping(target = "gioHangs", ignore = true)
    @Mapping(target = "manhTrangPhucs", source = "manhTrangPhucs", qualifiedByName = "convertManhTrangPhucs")
    @Mapping(target = "hinhAnhs", source = "hinhAnhs", qualifiedByName = "setHinhAnhs")
    TrangPhuc convert(UpdateTrangPhucDTO trangPhuc);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target="id", ignore = true)
    @Mapping(target = "theLoai", ignore = true)
    @Mapping(target = "hinhAnhs", ignore = true)
    @Mapping(target = "trangPhucChinh", ignore = true)
    @Mapping(target = "manhTrangPhucs", ignore = true)
    @Mapping(target = "gioHangs", ignore = true)
    @Mapping(target = "kichThuocTrangPhucs", ignore = true)
    @Mapping(target = "chiTietDonThues", ignore = true)
    void updateEntityFromDto(UpdateTrangPhucDTO trangPhucDTO, @MappingTarget TrangPhuc trangPhuc);
    @Named("hinhAnhsToString")
    default List<String> hinhAnhsToString(Set<HinhAnhTrangPhuc> hinhAnhs){
        return hinhAnhs.stream().map(HinhAnhTrangPhuc::getHinhAnh).collect(Collectors.toList());
    }
    @Named("StringToHinhAnhs")
    default Set<HinhAnhTrangPhuc> StringToHinhAnhs(List<String> hinhAnhs){
        return hinhAnhs.stream().map(HinhAnhTrangPhuc::new).collect(Collectors.toSet());
    }
    @Named("setHinhAnhs")
    default Set<HinhAnhTrangPhuc> setHinhAnhs(List<String> hinhAnhs){
        if(hinhAnhs == null) return null;
        return hinhAnhs.stream().map(hinh -> HinhAnhTrangPhuc.builder()
              .hinhAnh(hinh)
              .build()).collect(Collectors.toSet());
    }
    @Named("hasOrderCount")
    default int hasOrderCount(Set<ChiTietDonThue> chiTietDonThues){
        return chiTietDonThues.size();
    }
    @Mapping(target = "theLoai", source = "theLoai.maLoai" )
    @Mapping(target="hinhAnhs", source = "hinhAnhs", qualifiedByName = "hinhAnhsToString")
    @Mapping(target = "kichThuocs", source = "kichThuocTrangPhucs", qualifiedByName = "convertKichThuocToDTO")
    @Mapping(target="deleteManhTrangPhuc", ignore = true)
    @Mapping(target = "deleteKichThuoc", ignore = true)
    UpdateTrangPhucDTO converToUpdateDTO(TrangPhuc trangPhuc);

    @Named("convertKichThuocToDTO")
    default List<KichThuocTrangPhucDTO> convertKichThuocToDTO (Set<KichThuoc_TrangPhuc> kichThuocs){
            return kichThuocs.stream().map(KichThuocTrangPhucMapper.INSTANCE::convert)
                    .collect(Collectors.toList());
    }
    @Named("getQuantity")
    default int getQuantity(Set<KichThuoc_TrangPhuc> kichThuocs){
        int soLuong = 0;
        if(kichThuocs.size() == 1
                && Objects.equals(kichThuocs.stream().findFirst().get().getKichThuoc().getId(), "NONE")) {
            return 0;
        }
        for(KichThuoc_TrangPhuc kichThuoc : kichThuocs){
            soLuong += kichThuoc.getSoLuong();
        }
        return  soLuong;
    }
    @Named("convertManhTrangPhucs")
    default Set<TrangPhuc> convertManhTrangPhucs(List<UpdateTrangPhucDTO> dto){
        if(dto==null) return null;
        return dto.stream().map(this::convert)
               .collect(Collectors.toSet());
    }
}
