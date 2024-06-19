package com.stu.dissertation.clothingshop.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "khuyen_mai")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KhuyenMai {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="ma_khuyen_mai")
    private Long maKhuyenMai;
    @Column(name = "ngay_bat_dau", columnDefinition = "BIGINT NOT NULL")
    private Long ngayBatDau;
    @Column(name = "ngay_ket_thuc", columnDefinition = "BIGINT NOT NULL")
    private Long ngayKetThuc;
    @Column(name = "ten_khuyen_mai", columnDefinition = "VARCHAR(255) NOT NULL")
    private String tenKhuyenMai;
    @Column(name = "mo_ta", columnDefinition = "TEXT NOT NULL")
    private String moTa;
    @Column(name = "giam_toi_da", columnDefinition = "DECIMAL(10,2)")
    private BigDecimal giamToiDa;
    @Column(name = "phan_tram_giam", columnDefinition = "DOUBLE")
    private Double phanTramGiam;
    @Column(name = "so_luong_toi_thieu", columnDefinition = "INT NOT NULL")
    private Integer soLuongToiThieu;
    @Column(name = "giam_tien", columnDefinition = "DOUBLE")
    private Double giamTien;
    @ManyToMany(mappedBy ="khuyenMais")
    @JsonIgnore
    Set<TheLoai> theLoais;
}
