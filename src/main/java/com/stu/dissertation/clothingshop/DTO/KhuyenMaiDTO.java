package com.stu.dissertation.clothingshop.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class KhuyenMaiDTO {
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
}
