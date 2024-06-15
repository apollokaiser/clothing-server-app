package com.stu.dissertation.clothingshop.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name="trangphuc")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TrangPhuc extends BaseEntity {
    @Id
    @Column(name="ma_trang_phuc", columnDefinition = "VARCHAR(64)")
    private String id;
    @Column(name="ten_trang_phuc")
    private String tenTrangPhuc;
    @Column(name="giatien",columnDefinition = "DECIMAL(10,2) NOT NULL")
    private BigDecimal giaTien;
    @Column(name="discount",columnDefinition = "DECIMAL(10,2) NOT NULL" )
    private BigDecimal discount;
    @Column(name="mota_trang_phuc")
    private String moTa;
    @Column(name="soluong")
    private int soLuong;
    @Column(name="tinhtrang", columnDefinition = "BIT")
    private boolean tinhTrang;
    @ManyToOne
    @JoinColumn(
            name="maloai",
            referencedColumnName = "maloai",
            foreignKey = @ForeignKey(name = "FK_trangphuc_theloai")
    )
    @JsonIgnore
    private TheLoai theLoai;
    @OneToMany(mappedBy = "trangPhuc", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<HinhAnhTrangPhuc> hinhAnhs;
    @ManyToMany
    @JoinTable(
            name = "trangphuc_phukien",
            joinColumns = @JoinColumn(name = "ma_phukien", referencedColumnName = "ma_trang_phuc"),
            inverseJoinColumns = @JoinColumn(name = "ma_tranghuc_chinh", referencedColumnName = "ma_trang_phuc")
    )
    private Set<TrangPhuc> trangPhucChinhs;

    @ManyToMany(mappedBy = "trangPhucChinhs")
    private Set<TrangPhuc> phuKiens;

    @ManyToMany
    @JoinTable(name = "trangphuc_kichthuoc",
                joinColumns = @JoinColumn(name="ma_trang_phuc", referencedColumnName = "ma_trang_phuc"),
                inverseJoinColumns = @JoinColumn(name="ma_kich_thuoc", referencedColumnName = "ma_kich_thuoc")
    )
    private Set<KichThuoc> kichThuocs;

    @OneToMany(mappedBy = "trangPhuc")
    @JsonIgnore
    private Set<NguoiDung_GioHang> gioHangs;
}
