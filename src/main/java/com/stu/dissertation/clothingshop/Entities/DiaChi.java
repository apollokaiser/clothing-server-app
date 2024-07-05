package com.stu.dissertation.clothingshop.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name="dia_chi")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DiaChi extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_dia_chi", nullable = false)
    Long id;
    @Column(name = "ten_dia_chi", columnDefinition = "VARCHAR(100)")
    // vd: Nha rieng, truong hoc, ... (client)
    String tenDiaChi;
    @Column(name = "dia_chi", nullable = false, columnDefinition = "TEXT")
    String diaChi;
    @Column(name="mac_dinh", columnDefinition = "TINYINT(1)")
    Boolean isDefault;
    @ManyToOne
    @JoinColumn(name="ma_nguoi_dung",
            referencedColumnName = "ma_nguoi_dung",
            foreignKey = @ForeignKey(name = "FK_nguoidung_diachi")
    )
    @JsonIgnore
    private NguoiDung nguoiDung;
    @OneToOne(mappedBy = "diaChi",
    fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private AddressDetail detail;


}
