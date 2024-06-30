package com.stu.dissertation.clothingshop.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.stu.dissertation.clothingshop.Enum.PromotionType;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name="phieu_khuyen_mai")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PhieuKhuyenMai extends BaseEntity{
    @Id
    @Column(name="ma_phieu", columnDefinition = "VARCHAR(255) NOT NULL")
    private String maPhieu;
    @Column(name="ten_chuong_trinh", columnDefinition = "VARCHAR(255) NOT NULL")
    private String tenChuongTrinh;
    @Column(name="mo_ta", columnDefinition = "TEXT NOT NULL")
    private String moTa;
    @Column(name="ngay_bat_dau", columnDefinition = "BIGINT NOT NULL")
    private Long ngayBatDau;
    @Column(name="ngay_ket_thuc", columnDefinition = "BIGINT NOT NULL")
    private Long ngayKetThuc;
    @Column(name="gia_tri_giam", columnDefinition = "DECIMAL(10,2) NOT NULL")
    private Double giaTriGiam;
    @Column(name="giam_toi_da", columnDefinition = "DECIMAL(10,2) NOT NULL")
    private Double giamToiDa;
    @Column(name="don_vi", columnDefinition = "CHAR(20) NOT NULL")
    @Enumerated(EnumType.STRING)
    private PromotionType donVi;
    @Column(name="so_luong_nhap")
    private int soLuongNhap;
    @Column(name="khach_moi", columnDefinition = "TINYINT(1) NOT NULL")
    private boolean khachMoi;
    @OneToMany(mappedBy = "phieuKhuyenMai")
    @JsonIgnore
    private Set<DonThue> donThues;
}
