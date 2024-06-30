package com.stu.dissertation.clothingshop.DTO;

import com.stu.dissertation.clothingshop.Entities.DonThue;
import com.stu.dissertation.clothingshop.Entities.TrangPhuc;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
public class ChiTietDonThueDTO {
    private String maTrangPhuc;
    private String maKichThuoc;
    private int soLuong;
    private boolean toanPhan;
    private BigDecimal giaTien;
    private long khuyenMai;
    private BigDecimal discount;
    private BigDecimal tongTien;
}
