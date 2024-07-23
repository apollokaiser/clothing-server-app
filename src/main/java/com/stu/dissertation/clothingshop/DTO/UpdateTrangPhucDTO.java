package com.stu.dissertation.clothingshop.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateTrangPhucDTO {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String id;
    private String tenTrangPhuc;
    private BigDecimal giaTien;
    private String moTa;
    private int soLuong;
    private Long theLoai;
    private List<String> hinhAnhs;
    private List<KichThuocTrangPhucDTO> kichThuocs;
}
