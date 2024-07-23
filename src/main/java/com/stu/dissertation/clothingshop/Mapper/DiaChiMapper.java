package com.stu.dissertation.clothingshop.Mapper;

import com.stu.dissertation.clothingshop.DTO.DiaChiDTO;
import com.stu.dissertation.clothingshop.Entities.AddressDetail;
import com.stu.dissertation.clothingshop.Entities.DiaChi;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface DiaChiMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target ="nguoiDung", ignore = true)
    @Mapping(target ="detail", ignore = true)
    DiaChi convert(DiaChiDTO diaChiDTO);
    @Named(value = "getAddressDetail")
    default AddressDetail getAddressDetail(DiaChiDTO diaChi){
        if(diaChi==null) return null;
       return AddressDetail.builder()
                .provinceId(diaChi.getProvinceId())
                .districtId(diaChi.getDistrictId())
                .wardId(diaChi.getWardId())
                .build();
    }
}
