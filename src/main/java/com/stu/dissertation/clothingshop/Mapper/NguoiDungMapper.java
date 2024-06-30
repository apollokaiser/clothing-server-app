package com.stu.dissertation.clothingshop.Mapper;

import com.stu.dissertation.clothingshop.DTO.NguoiDungDetailDTO;
import com.stu.dissertation.clothingshop.DTO.TaiKhoanDTO;
import com.stu.dissertation.clothingshop.Entities.NguoiDung;
import com.stu.dissertation.clothingshop.Entities.NguoiDung_GioHang;
import com.stu.dissertation.clothingshop.Entities.TaiKhoanLienKet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface NguoiDungMapper {
    @Mapping(target = "taiKhoan", source = "taiKhoan", qualifiedByName = "getTaiKhoan")
    @Mapping(target="gioHangs", source = "gioHangs", qualifiedByName = "getCartQuantity")
    NguoiDungDetailDTO convert(NguoiDung user);
    @Named("getTaiKhoan")
    default TaiKhoanDTO getTaiKhoan(TaiKhoanLienKet taikhoan){
        return TaiKhoanMapper.INSTANCE.convert(taikhoan);
    }
    @Named("getCartQuantity")
    default Integer getCartQuantity(Set<NguoiDung_GioHang> gioHangs){
        return gioHangs.size();
    }
}
