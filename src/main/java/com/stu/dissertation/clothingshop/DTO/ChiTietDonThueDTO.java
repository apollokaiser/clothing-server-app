package com.stu.dissertation.clothingshop.DTO;

import com.stu.dissertation.clothingshop.Entities.Embedded.TrangPhuc_KichThuocKey;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
public class ChiTietDonThueDTO {
    private TrangPhuc_KichThuocKey outfitSizeId;
    private int soLuong;
    private BigDecimal giaTien;
    private long khuyenMai;
    private BigDecimal discount;
    private BigDecimal tongTien;
    private TrangPhucPreviewDTO trangPhuc = null;
}
