package com.stu.dissertation.clothingshop.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class KichThuocTrangPhucDTO {
    private String maKichThuoc;
    private Integer soLuong = null;
}
