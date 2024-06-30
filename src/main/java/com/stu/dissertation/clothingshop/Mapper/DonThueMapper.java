package com.stu.dissertation.clothingshop.Mapper;

import com.stu.dissertation.clothingshop.DTO.DonThueDTO;
import com.stu.dissertation.clothingshop.Entities.DonThue;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Date;

@Mapper(componentModel = "spring")
public interface DonThueMapper {
    @Mapping(target = "nguoiDung", ignore = true)
    @Mapping(target="phieuKhuyenMai", ignore = true)
    @Mapping(target="payment", ignore = true)
    @Mapping(target="chiTietDonThues", ignore = true)
    @Mapping(target = "trangThai", ignore = true)
    @Mapping(target="maDonThue", ignore = true)
    @Mapping(target = "ngayThue", ignore = true)
    @Mapping(target="ngayNhan", source = "ngayNhan", qualifiedByName = "getLongDate")
    DonThue convert(DonThueDTO donThue);

    @Named("getLongDate")
    default Long getLongDate(Date ngayNhan) {
        return ngayNhan.getTime();
    }
}
