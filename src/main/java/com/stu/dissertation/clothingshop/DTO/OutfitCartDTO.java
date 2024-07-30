package com.stu.dissertation.clothingshop.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OutfitCartDTO {
    private String id;
    private String tenTrangPhuc;
    private BigDecimal giaTien;
    private boolean tinhTrang;
    private long theLoai;
    private String hinhAnh;
    private Set<OutfitCartDTO> manhTrangPhucs;
    private Set<KichThuocTrangPhucDTO> kichThuocs;
}
