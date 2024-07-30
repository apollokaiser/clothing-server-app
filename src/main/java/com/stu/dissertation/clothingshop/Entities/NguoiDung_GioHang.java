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
public class NguoiDung_GioHang {
    @EmbeddedId
    private Nguoidung_GioHangKey id;
    @ManyToOne
    @MapsId("maNguoiDung")
    @JoinColumn(name = "ma_nguoi_dung")
    @JsonIgnore
    NguoiDung nguoiDung;
    @MapsId("outfitSizeId")
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "ma_kich_thuoc", referencedColumnName = "ma_kich_thuoc"),
            @JoinColumn(name = "ma_trang_phuc", referencedColumnName = "ma_trang_phuc")
    })
    private KichThuoc_TrangPhuc outfitSize;
    @Column(name="so_luong", columnDefinition = "INT NOT NULL")
    private Integer soLuong;
    // mã trang phục chính của cái trang phục có kích thước ở trên
    @ManyToOne
    @JoinColumn(name="ma_trang_phuc_chinh", referencedColumnName = "ma_trang_phuc")
    @JsonIgnore
    private TrangPhuc trangPhucChinh;
}
