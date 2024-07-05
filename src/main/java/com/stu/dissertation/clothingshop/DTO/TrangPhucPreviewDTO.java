package com.stu.dissertation.clothingshop.DTO;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class TrangPhucPreviewDTO {
    private String id;
    private String tenTrangPhuc;
    private BigDecimal giaTien;
    private BigDecimal giaTronBo;
}
