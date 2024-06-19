package com.stu.dissertation.clothingshop.DTO;

import com.stu.dissertation.clothingshop.Entities.DiaChi;
import com.stu.dissertation.clothingshop.Entities.NguoiDung_GioHang;
import com.stu.dissertation.clothingshop.Entities.TaiKhoanLienKet;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class NguoiDungDetailDTO {
    private String tenNguoiDung;
    private Boolean khachMoi;
    private String sdt;
    private Set<DiaChi> diaChis;
    private Set<TaiKhoanDTO> taiKhoans;
    private Set<NguoiDung_GioHang> gioHangs;
}
