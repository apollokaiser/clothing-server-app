package com.stu.dissertation.clothingshop.DTO;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class KhuyenMaiDTO {
    private Long maKhuyenMai;
    private Long ngayBatDau;
    private Long ngayKetThuc;
    private Double giamTien;
    private String tenKhuyenMai;
    private String moTa;
    private BigDecimal giamToiDa;
    private Double phanTramGiam;
    private Integer soLuongToiThieu;
    private Double giaTriToiThieu;
}
