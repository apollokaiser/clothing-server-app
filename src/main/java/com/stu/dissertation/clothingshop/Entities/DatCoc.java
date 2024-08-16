package com.stu.dissertation.clothingshop.Entities;

import com.stu.dissertation.clothingshop.Enum.PaymentMethod;
import com.stu.dissertation.clothingshop.Enum.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name="dat_coc")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DatCoc {
    @Id
    private String id;
    @Column(name="ngay_coc", columnDefinition = "BIGINT NOT NULL")
    private Long ngayCoc;
    @Column(name="transaction_id", columnDefinition = "VARCHAR(255)")
    private String transactionId;
    @Column(name="so_tien", columnDefinition = "DECIMAL(10,2) NOT NULL")
    private BigDecimal soTien;
    @Column(name="noi_dung", columnDefinition = "TEXT")
    private String noiDungDatCoc;
    @Column(name="phuong_thuc", columnDefinition = "CHAR(20) NOT NULL")
    @Enumerated(EnumType.STRING)
    private PaymentMethod payment;
    @Column(name="trang_thai", columnDefinition = "VARCHAR(255) NOT NULL")
    @Enumerated(EnumType.STRING)
    private PaymentStatus trangThai;
    @OneToOne
    @MapsId
    @JoinColumn(name="ma_don_thue")
    private DonThue donThue;
}
