package com.stu.dissertation.clothingshop.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="hinhanh_trangphuc")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HinhAnhTrangPhuc extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long maHinh;
    @Column(name="hinh_anh", columnDefinition = "VARCHAR(255) NOT NULL")
    private String hinhAnh;
    @ManyToOne
    @JoinColumn(
            name="ma_trang_phuc",
            referencedColumnName = "ma_trang_phuc",
            foreignKey = @ForeignKey(name="FK_hinhanh_trangphuc")
    )
    @JsonBackReference
    private TrangPhuc trangPhuc;

    public HinhAnhTrangPhuc(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }
}
