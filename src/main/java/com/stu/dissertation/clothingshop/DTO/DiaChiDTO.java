package com.stu.dissertation.clothingshop.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DiaChiDTO {
    private String tenDiaChi;
    private String diaChi;
    private Boolean isDefault;
    private int provinceId;
    private int districtId;
    private int wardId;
}
