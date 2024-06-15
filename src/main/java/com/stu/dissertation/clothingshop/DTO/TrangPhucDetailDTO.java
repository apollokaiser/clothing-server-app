package com.stu.dissertation.clothingshop.DTO;

import com.stu.dissertation.clothingshop.Entities.HinhAnhTrangPhuc;
import com.stu.dissertation.clothingshop.Entities.KichThuoc;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Data
public class TrangPhucDetailDTO {
    private String id;
    private String tenTrangPhuc;
    private BigDecimal giaTien;
    private BigDecimal discount;
    private String moTa;
    private int soLuong;
    private boolean tinhTrang;
    private long theLoai; // Kiểu int cho thuộc tính theLoai
    private Set<String> hinhAnhs; // Danh sách hinhAnhs
    private Set<TrangPhucDetailDTO> phuKiens;
    private Set<KichThuoc> kichThuocs;
}
