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
    private String moTa;
    private boolean tinhTrang;
    private long theLoai;
    private Set<String> hinhAnhs;
    private Set<TrangPhucDetailDTO> manhTrangPhucs;
    private Set<KichThuocTrangPhucDTO> kichThuocs;
}
