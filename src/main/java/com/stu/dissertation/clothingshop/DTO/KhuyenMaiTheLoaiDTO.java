package com.stu.dissertation.clothingshop.DTO;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class KhuyenMaiTheLoaiDTO {
    private Long maKhuyenMai;
    private Long ngayBatDau;
    private Long ngayKetThuc;
    private Double giamTien;
    private String tenKhuyenMai;
    private String moTa;
    private String noiDungChinh;
    private BigDecimal giamToiDa;
    private Double phanTramGiam;
    private Integer soLuongToiThieu;
    private Double giaTriToiThieu;
    private List<Long> theLoais;
}
