package com.stu.dissertation.clothingshop.DTO;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
public class TrangPhucDetailDTO {
    private String id;
    private String tenTrangPhuc;
    private BigDecimal giaTien;
    private BigDecimal giaTronBo;
    private String moTa;
    private int soLuong;
    private boolean tinhTrang;
    private boolean phoiSan;
    private String tenPhanLoai;
    private long theLoai;
    private Set<String> hinhAnhs;
    private Set<TrangPhucDetailDTO> phuKiens;
    private Set<KichThuocTrangPhucDTO> kichThuocs;
}
