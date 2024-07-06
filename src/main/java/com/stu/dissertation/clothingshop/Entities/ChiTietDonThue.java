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
@IdClass(ChiTietDonThueKey.class)
public class ChiTietDonThue{
    @Id
    @Column(name="ma_chi_tiet", columnDefinition = "VARCHAR(255)")
    private String maChiTiet;
    @Column(name="ma_kich_thuoc", columnDefinition = "CHAR(10)")
    private String maKichThuoc;
    @Column(name="so_luong", columnDefinition = "INT NOT NULL")
    private int soLuong;
    @Column(name="is_full_outfit", columnDefinition = "TINYINT(1) NOT NULL")
    private boolean toanPhan;
    @Column(name="gia_tien", columnDefinition = "DECIMAL(10,2) NOT NULL")
    private BigDecimal giaTien;
    @Column(name="ma_khuyen_mai", columnDefinition = "BIGINT")
    private long khuyenMai;
    @Column(name="giamGia", columnDefinition = "DECIMAL(10,2) NOT NULL")
    private BigDecimal discount;
    @Column(name="tong_tien", columnDefinition = "DECIMAL(10,2) NOT NULL")
    private BigDecimal tongTien;
    @ManyToOne
    @Id
    @JoinColumn(name = "ma_trang_phuc",
            referencedColumnName = "ma_trang_phuc",
            foreignKey = @ForeignKey(name="FK_ctdonthue_trangphuc"))
    @JsonIgnore
    private TrangPhuc trangPhuc;
    @ManyToOne
    @Id
    @JoinColumn(name = "ma_don_thue",
            referencedColumnName = "ma_don_thue",
            foreignKey = @ForeignKey(name = "FK_ctdonthue_donthue"))
    @JsonIgnore
    private DonThue donThue;
}
