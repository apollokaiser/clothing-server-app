package com.stu.dissertation.clothingshop.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.stu.dissertation.clothingshop.Entities.Embedded.TrangPhuc_KichThuocKey;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name="kichthuoc_trangphuc")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KichThuoc_TrangPhuc {
    @EmbeddedId
    private TrangPhuc_KichThuocKey id;
    @ManyToOne
    @MapsId("maTrangPhuc")
    @JoinColumn(name = "ma_trang_phuc")
    @JsonIgnore
    private TrangPhuc trangPhuc;
    @ManyToOne
    @MapsId("maKichThuoc")
    @JoinColumn(name = "ma_kich_thuoc")
    @JsonIgnore
    private KichThuoc kichThuoc;
    @Column(name="so_luong", columnDefinition = "INT NOT NULL DEFAULT 0")
    private Integer soLuong;
    @Column(name="ton_kho", columnDefinition = "INT NOT NULL DEFAULT 0")
    private Integer tonKho;
    @Column(name="trang_thai", columnDefinition = "TINYINT(1) NOT NULL DEFAULT 1")
    private Boolean trangThai = true;
    @OneToMany(mappedBy="outfitSize")
    @JsonIgnore
    private Set<NguoiDung_GioHang> gioHangs;
    @OneToMany(mappedBy="outfitSize")
    @JsonIgnore
    private Set<ChiTietDonThue> chiTietDons;
}
