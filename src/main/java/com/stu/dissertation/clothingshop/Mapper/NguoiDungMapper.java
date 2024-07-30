package com.stu.dissertation.clothingshop.Mapper;

import com.stu.dissertation.clothingshop.DTO.AdminStaffDTO;
import com.stu.dissertation.clothingshop.DTO.AdminStaffDetailDTO;
import com.stu.dissertation.clothingshop.DTO.NguoiDungDetailDTO;
import com.stu.dissertation.clothingshop.DTO.TaiKhoanDTO;
import com.stu.dissertation.clothingshop.Entities.*;
import com.stu.dissertation.clothingshop.Enum.ROLE;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface NguoiDungMapper {
    @Named("getAdminRole")
    default String getAdminRole(Set<Role> roles){
        return roles.stream().filter(r->
                        !r.getRole().equals(ROLE.ADMIN.getRole()) && !r.getRole().equals(ROLE.USER.getRole()))
                .findFirst().get().getRole();
    }
    @Named("getDonThueCount")
    default int getDonThueCount(Set<DonThue> donThues){
        return donThues.size();
    }
    @Named("getCreatedBy")
    default String getCreatedBy(NguoiDung user){
        return user.getCreatedBy();
    }
    @Named("getCreateAt")
    default Long getCreateAt(NguoiDung user){
        if(user.getCreateAt()==null) return null;
        return user.getCreateAt()/1000;
    }
    @Named("getTaiKhoan")
    default TaiKhoanDTO getTaiKhoan(TaiKhoanLienKet taikhoan){
        return TaiKhoanMapper.INSTANCE.convert(taikhoan);
    }
    @Named("getCartQuantity")
    default Integer getCartQuantity(Set<NguoiDung_GioHang> gioHangs){
        return gioHangs.size();
    }
    @Mapping(target = "taiKhoan", source = "taiKhoan", qualifiedByName = "getTaiKhoan")
    @Mapping(target="gioHangs", source = "gioHangs", qualifiedByName = "getCartQuantity")
    NguoiDungDetailDTO convert(NguoiDung user);
    @Mapping(target = "role", source = "roles", qualifiedByName = "getAdminRole")
    AdminStaffDTO convertAdminDTO(NguoiDung user);
    @Mapping(target = "role", source = "roles", qualifiedByName = "getAdminRole")
    @Mapping(target="soDonThue", source = "donThues", qualifiedByName = "getDonThueCount")
    @Mapping(target = "createdBy",source = "admin", qualifiedByName = "getCreatedBy")
    @Mapping(target="createAt",source = "admin", qualifiedByName = "getCreateAt")
    AdminStaffDetailDTO convertAdminStaffDetailDTO(NguoiDung admin);
}
