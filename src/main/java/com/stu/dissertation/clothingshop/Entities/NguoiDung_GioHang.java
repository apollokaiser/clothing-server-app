package com.stu.dissertation.clothingshop.Entities;

import com.stu.dissertation.clothingshop.Entities.Embedded.Nguoidung_GioHangKey;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="nguoidung_giohang")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NguoiDung_GioHang {

    @EmbeddedId
    private Nguoidung_GioHangKey id;
    @ManyToOne
    @MapsId("maNguoiDung")
    @JoinColumn(name = "ma_nguoi_dung")
    NguoiDung nguoiDung;

    @ManyToOne
    @MapsId("maTrangPhuc")
    @JoinColumn(name = "ma_trang_phuc")
    TrangPhuc trangPhuc;

    @ManyToOne
    @MapsId("maKichThuoc")
    @JoinColumn(name="ma_kich_thuoc")
    private KichThuoc kichThuoc;
    @Column(name="so_luong", columnDefinition = "INT NOT NULL")
    private int soLuong;
    @Column(name="is_full_outfit", columnDefinition = "TINYINT(1) NOT NULL")
    private boolean toanPhan;
}
