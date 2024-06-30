package com.stu.dissertation.clothingshop.DTO;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class KhuyenMaiThanhToanDTO {
    private Long maKhuyenMai;
    private String tenKhuyenMai;
    private String moTa;
    private BigDecimal giamToiDa;
    private Double phanTramGiam;
    private Integer soLuongToiThieu;
    private Double giaTriToiThieu;
}
