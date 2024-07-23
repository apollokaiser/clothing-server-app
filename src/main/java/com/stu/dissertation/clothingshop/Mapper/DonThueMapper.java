package com.stu.dissertation.clothingshop.Mapper;

import com.stu.dissertation.clothingshop.DTO.ChiTietDonThueDTO;
import com.stu.dissertation.clothingshop.DTO.DatCocDTO;
import com.stu.dissertation.clothingshop.DTO.DonThueDTO;
import com.stu.dissertation.clothingshop.DTO.DonThuePreviewDTO;
import com.stu.dissertation.clothingshop.Entities.ChiTietDonThue;
import com.stu.dissertation.clothingshop.Entities.DatCoc;
import com.stu.dissertation.clothingshop.Entities.DonThue;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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


    @Mapping(target = "phieuKhuyenMai", ignore = true)
    @Mapping(target = "payment", ignore = true)
    @Mapping(target = "nguoiDung", source = "nguoiDung.id")
    @Mapping(target = "ngayNhan", source = "ngayNhan", qualifiedByName = "getDate")
    @Mapping(target = "ngayThue", source = "ngayThue", qualifiedByName = "getDate")
    @Mapping(target = "chiTiet", source = "chiTietDonThues", qualifiedByName = "getChiTietThue")
    @Mapping(target = "datCoc", source ="datCoc", qualifiedByName = "getDatCoc")
    DonThueDTO convert(DonThue donThue);

    DonThuePreviewDTO convertPreview(DonThue donThue);

    @Named("getLongDate")
    default Long getLongDate(Date ngayNhan) {
        return ngayNhan.getTime()/1000;
    }
    @Named("getDate")
    default Date getDate(Long ngayNhan) {
        return new Date(ngayNhan);
    }
    @Named("getChiTietThue")
    default List<ChiTietDonThueDTO> getChiTietThue(Set<ChiTietDonThue> chiTietDonThues) {
        return chiTietDonThues.stream().map(ChiTietDonThueMapper.INSTANCE::convert)
                .collect(Collectors.toList());
    }
    @Named("getDatCoc")
    default DatCocDTO getDatCoc(DatCoc datCoc) {
        if(datCoc==null) return null;
        return DatCocMapper.INSTANCE.convert(datCoc);
    }

}
