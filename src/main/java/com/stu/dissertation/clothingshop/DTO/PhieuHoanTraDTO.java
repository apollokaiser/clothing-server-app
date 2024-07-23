package com.stu.dissertation.clothingshop.DTO;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class PhieuHoanTraDTO {
    private String id;
    private BigDecimal phuThu;
    private int quaHan;
    private BigDecimal tongTien;
    private String trangThai;
}
