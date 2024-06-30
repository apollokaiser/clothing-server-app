package com.stu.dissertation.clothingshop.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.stu.dissertation.clothingshop.Entities.*;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class DonThueDTO {
    private String tenNguoiNhan;
    private String sdtNguoiNhan;
    private String diaChiNguoiNhan;
    private String ghiChu;
    private Date ngayNhan;
    private BigDecimal tamTinh;
    private BigDecimal tongUuDai;
    private BigDecimal tongThue;
    private Long nguoiDung;
    private String phieuKhuyenMai;
    private String payment;
    private List<ChiTietDonThueDTO> chiTiet;
}
