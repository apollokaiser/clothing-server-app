package com.stu.dissertation.clothingshop.Mapper;

import com.stu.dissertation.clothingshop.DTO.NguoiDungDetailDTO;
import com.stu.dissertation.clothingshop.DTO.TaiKhoanDTO;
import com.stu.dissertation.clothingshop.Entities.NguoiDung;
import com.stu.dissertation.clothingshop.Entities.TaiKhoanLienKet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface NguoiDungMapper {
    @Mapping(target = "taiKhoans", source = "taiKhoans", qualifiedByName = "getTaiKhoans")
    NguoiDungDetailDTO convert(NguoiDung user);
    @Named("getTaiKhoans")
    default Set<TaiKhoanDTO> getTaiKhoans(Set<TaiKhoanLienKet> taikhoan){
        return taikhoan.stream()
                .map(TaiKhoanMapper.INSTANCE::convert)
                .collect(Collectors.toSet());
    }
}
