package com.stu.dissertation.clothingshop.DTO;


import com.stu.dissertation.clothingshop.Entities.TheLoai;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class TrangPhucDTO {

    private String id;

    private String tenTrangPhuc;

    private BigDecimal giaTien;

    private BigDecimal discount;

    private List<String> hinhAnhs;

}
