package com.stu.dissertation.clothingshop.DTO;

import com.stu.dissertation.clothingshop.Entities.DiaChi;
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
    private TaiKhoanDTO taiKhoan;
    private Integer gioHangs;
}
