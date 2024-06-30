package com.stu.dissertation.clothingshop.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.stu.dissertation.clothingshop.Enum.PaymentMethod;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name="don_thue")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DonThue extends BaseEntity{
    @Id
    @Column(name="ma_don_thue", columnDefinition = "VARCHAR(100)")
    private String maDonThue;
    @Column(name="ten_nguoi_nhan", columnDefinition = "VARCHAR(255) NOT NULL")
    private String tenNguoiNhan;
    @Column(name="sdt_nguoi_nhan", columnDefinition = "VARCHAR(20) NOT NULL")
    private String sdtNguoiNhan;
    @Column(name="dia_chi_nguoi_nhan", columnDefinition = "TEXT NOT NULL")
    private String diaChiNguoiNhan;
    @Column(name="ghi_chu", columnDefinition = "TEXT")
    private String ghiChu;
    @Column(name="ngay_thue", columnDefinition = "BIGINT NOT NULL")
    private Long ngayThue;
    @Column(name="ngay_nhan", columnDefinition = "BIGINT NOT NULL")
    private Long ngayNhan;
    @Column(name="tam_tinh", columnDefinition = "DECIMAL(10,2) NOT NULL")
    private BigDecimal tamTinh;
    @Column(name="tong_uu_dai", columnDefinition = "DECIMAL(10,2) NOT NULL")
    private BigDecimal tongUuDai;
    @Column(name="tong_thue", columnDefinition = "DECIMAL(10,2) NOT NULL")
    private BigDecimal tongThue;
    @Enumerated(EnumType.STRING)
    private PaymentMethod payment;
    @ManyToOne
    @JoinColumn(name = "ma_trang_thai",
            referencedColumnName = "ma_trang_thai",
            foreignKey = @ForeignKey(name = "FK_donthue_trangthai"))
    private TrangThai trangThai;
    @ManyToOne
    @JoinColumn(name = "ma_nguoi_dung",
            referencedColumnName = "ma_nguoi_dung",
            foreignKey = @ForeignKey(name = "FK_donthue_nguoidung"))
    private NguoiDung nguoiDung;
    @ManyToOne
    @JoinColumn(name="ma_phieu", referencedColumnName = "ma_phieu"
    )
    private PhieuKhuyenMai phieuKhuyenMai;
    @OneToMany(mappedBy = "donThue", cascade = CascadeType.ALL)
    private Set<ChiTietDonThue> chiTietDonThues;
    @OneToOne(mappedBy = "donThue")
    @JsonIgnore
    private PhieuHoanTra phieuTra;
}
