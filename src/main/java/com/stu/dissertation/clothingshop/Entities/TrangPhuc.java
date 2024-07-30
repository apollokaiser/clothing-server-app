package com.stu.dissertation.clothingshop.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.stu.dissertation.clothingshop.Enum.BusinessErrorCode;
import com.stu.dissertation.clothingshop.Enum.SIZE;
import com.stu.dissertation.clothingshop.Exception.CustomException.ApplicationException;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;

import java.math.BigDecimal;
import java.util.Objects;
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
    @Column(name="ten_trang_phuc",nullable = false)
    private String tenTrangPhuc;
    @Column(name="gia_thue",columnDefinition = "DECIMAL(10,2) NOT NULL")
    private BigDecimal giaTien;
    @Column(name="mota_trangphuc", columnDefinition = "TEXT")
    private String moTa;
    @Column(name="tinh_trang", columnDefinition = "BIT")
    private boolean tinhTrang;
    @Column(name = "has_piece", columnDefinition = "TINYINT(1)")
    private boolean hasPiece;
    @ManyToOne
    @JoinColumn(
            name="maloai",
            referencedColumnName = "maloai",
            foreignKey = @ForeignKey(name = "FK_trangphuc_theloai")
    )
    @JsonIgnore
    private TheLoai theLoai;
    @OneToMany(mappedBy = "trangPhuc", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<HinhAnhTrangPhuc> hinhAnhs;
    @ManyToOne
    @JoinColumn(name = "ma_trang_phuc_chinh", referencedColumnName = "ma_trang_phuc")
    @JsonIgnore
    private TrangPhuc trangPhucChinh;
    @OneToMany(mappedBy = "trangPhucChinh", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<TrangPhuc> manhTrangPhucs;
    @OneToMany(mappedBy = "trangPhucChinh", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private Set<NguoiDung_GioHang> gioHangs;

    @OneToMany(mappedBy = "trangPhuc", cascade = CascadeType.ALL, fetch = FetchType.LAZY,orphanRemoval = true)
    @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
    private Set<KichThuoc_TrangPhuc> kichThuocTrangPhucs;

    @OneToMany(mappedBy = "trangPhucChinh")
    @JsonIgnore
    private Set<ChiTietDonThue> chiTietDonThues;
    @PrePersist
    @PreUpdate
    public void checkAccessaryItem() {
        if(theLoai.getForAccessary()) {
            KichThuoc_TrangPhuc kichThuocTrangPhuc = kichThuocTrangPhucs.iterator().next();
            //NOTE: Kiểm tra xem đạo cụ có phải là có kích thước là none không
            if(!Objects.equals(kichThuocTrangPhuc.getKichThuoc().getId(), SIZE.NONE.name()) && kichThuocTrangPhucs.size()!=1) {
                throw new ApplicationException(BusinessErrorCode.NOT_ALLOW_DATA_SOURCE, "Accessary item must set size that is none");
            }
        }
    }
}
