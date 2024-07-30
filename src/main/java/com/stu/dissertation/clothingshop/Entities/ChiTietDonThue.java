package com.stu.dissertation.clothingshop.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.stu.dissertation.clothingshop.Entities.Embedded.ChiTietDonThueKey;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name="chi_tiet_don_thue")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChiTietDonThue{
    @EmbeddedId
    private ChiTietDonThueKey id;
    @Column(name="so_luong", columnDefinition = "INT NOT NULL")
    private int soLuong;
    @Column(name="gia_tien", columnDefinition = "DECIMAL(10,2) NOT NULL")
    private BigDecimal giaTien;
    @Column(name="ma_khuyen_mai", columnDefinition = "BIGINT")
    private long khuyenMai;
    @Column(name="giamGia", columnDefinition = "DECIMAL(10,2) NOT NULL")
    private BigDecimal discount;
    @Column(name="tong_tien", columnDefinition = "DECIMAL(10,2) NOT NULL")
    private BigDecimal tongTien;
    @ManyToOne
    @MapsId("maDonThue")
    @JoinColumn(name = "ma_don_thue")
    @JsonIgnore
    private DonThue donThue;
    @MapsId("outfitSizeId")
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "ma_kich_thuoc", referencedColumnName = "ma_kich_thuoc"),
            @JoinColumn(name = "ma_trang_phuc", referencedColumnName = "ma_trang_phuc")
    })
    private KichThuoc_TrangPhuc outfitSize;

    @ManyToOne
    @JoinColumn(
            name="ma_trang_phuc_chinh",
            referencedColumnName = "ma_trang_phuc")
    @JsonIgnore
    private TrangPhuc trangPhucChinh;
}
