package com.stu.dissertation.clothingshop.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name="phieu_hoan_tra")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PhieuHoanTra {
    @Id
    private String id;
    private Long ngayTra;
    @Column(name="phu_thu", columnDefinition = "DECIMAL(10, 2)")
    private BigDecimal phuThu;
    @Column(name="qua_han", columnDefinition = "INT")
    private int quaHan;
    private BigDecimal tongTien;
    private String trangThai;
    @OneToOne
    @MapsId
    @PrimaryKeyJoinColumn(name="ma_don_thue")
    private DonThue donThue;
}
