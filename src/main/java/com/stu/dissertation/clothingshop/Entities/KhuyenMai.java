package com.stu.dissertation.clothingshop.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "khuyen_mai")
@NamedStoredProcedureQuery(
        name = "PROC_add_promotion",
        procedureName = "PROC_add_promotion",
        parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "promotion_id", type = Long.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "category_id", type = Long.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "this_time", type = Long.class),
                @StoredProcedureParameter(mode = ParameterMode.OUT, name = "result", type = Integer.class)
        }
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KhuyenMai extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="ma_khuyen_mai", columnDefinition = "BIGINT")
    private Long maKhuyenMai;
    @Column(name = "ngay_bat_dau", columnDefinition = "BIGINT NOT NULL")
    private Long ngayBatDau;
    @Column(name = "ngay_ket_thuc", columnDefinition = "BIGINT NOT NULL")
    private Long ngayKetThuc;
    @Column(name = "ten_khuyen_mai", columnDefinition = "VARCHAR(255) NOT NULL")
    private String tenKhuyenMai;
    @Column(name = "mo_ta", columnDefinition = "TEXT NOT NULL")
    private String moTa;
    @Column(name="noi_dung_chinh", columnDefinition = "TEXT NOT NULL")
    private String noiDungChinh;
    @Column(name = "giam_toi_da", columnDefinition = "DECIMAL(10,2)")
    private BigDecimal giamToiDa;
    @Column(name = "phan_tram_giam", columnDefinition = "DOUBLE")
    private Double phanTramGiam;
    @Column(name = "so_luong_toi_thieu", columnDefinition = "INT NOT NULL")
    private Integer soLuongToiThieu;
    @Column(name = "gia_tri_toi_thieu", columnDefinition = "DECIMAL(10,2)")
    private Double giaTriToiThieu;
    @Column(name = "giam_tien", columnDefinition = "DECIMAL(10,2)")
    private Double giamTien;
    @ManyToMany(mappedBy ="khuyenMais")
    @JsonIgnore
    Set<TheLoai> theLoais;
}
