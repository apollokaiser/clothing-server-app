package com.stu.dissertation.clothingshop.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.stu.dissertation.clothingshop.Entities.TrangThai;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DonThuePreviewDTO {
    private String maDonThue;
    private String tenNguoiNhan;
    private String sdtNguoiNhan;
    private String diaChiNguoiNhan;
    private Long ngayNhan;
    private Long ngayThue;
    private BigDecimal tongThue;
    private String payment;
    private TrangThai trangThai = null;
}
