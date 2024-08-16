package com.stu.dissertation.clothingshop.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.stu.dissertation.clothingshop.Entities.*;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

//@Getter
//@Setter
@Jacksonized
@Builder
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DonThueDTO {
    private String tenNguoiNhan;
    private String sdtNguoiNhan;
    private String diaChiNguoiNhan;
    private String ghiChu;
    private Date ngayNhan;
    private Date ngayThue = new Date();
    private BigDecimal tamTinh;
    private BigDecimal tongUuDai;
    private BigDecimal tongThue;
    private String nguoiDung;
    private String phieuKhuyenMai;
    private BigDecimal theChan;
    private String payment;
    private List<ChiTietDonThueDTO> chiTiet;
    private TrangThai trangThai = null;
    private DatCocDTO datCoc = null;
}
