package com.stu.dissertation.clothingshop.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.stu.dissertation.clothingshop.Entities.Embedded.Nguoidung_GioHangKey;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="nguoidung_giohang")
@NamedStoredProcedureQuery(
        name = "PROC_add_cart",
        procedureName = "PROC_add_cart",
        parameters = {
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "ma_nguoi_dung", type = String.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "add_list", type = String.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "delete_list", type = String.class)
        }
)
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@IdClass(Nguoidung_GioHangKey.class)
public class NguoiDung_GioHang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="gio_hang_id", columnDefinition = "BIGINT NOT NULL")
    private Long gioHangId;
    @Id
    @Column(name="ma_trang_phuc", columnDefinition = "VARCHAR(64) NOT NULL")
    private String maTrangPhuc;
    @Id
    @Column(name="ma_nguoi_dung", columnDefinition = "BIGINT NOT NULL")
    private String maNguoiDung;
    @ManyToOne
    @MapsId("maNguoiDung")
    @JoinColumn(name = "ma_nguoi_dung")
    @JsonIgnore
    NguoiDung nguoiDung;

    @ManyToOne
    @MapsId("maTrangPhuc")
    @JoinColumn(name = "ma_trang_phuc")
    @JsonIgnore
    private
    TrangPhuc trangPhuc;
    @Column(name="ma_kich_thuoc", columnDefinition = "CHAR(10)")
    private String kichThuoc;
    @Column(name="so_luong", columnDefinition = "INT NOT NULL")
    private int soLuong;
    @Column(name="is_full_outfit", columnDefinition = "TINYINT(1) NOT NULL")
    private boolean toanPhan;
}
