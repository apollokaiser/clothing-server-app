package com.stu.dissertation.clothingshop.DTO;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class TrangPhucDTO {
    private String id;
    private String tenTrangPhuc;
    private BigDecimal giaTien;
    private List<String> hinhAnhs;
    private int soLuong;
    private int hasOrderCount;

}
