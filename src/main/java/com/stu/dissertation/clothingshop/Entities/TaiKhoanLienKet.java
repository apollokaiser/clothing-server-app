package com.stu.dissertation.clothingshop.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.stu.dissertation.clothingshop.Enum.LoginType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="tai_khoan_lien_ket")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaiKhoanLienKet extends BaseEntity {
    @Id
    private String id;
    @Enumerated(EnumType.STRING)
    @Column(name = "provider", columnDefinition = "CHAR(10) NOT NULL")
    private LoginType provider;
    @OneToOne
    @MapsId
    @PrimaryKeyJoinColumn(name = "ma_nguoi_dung")
    private NguoiDung nguoiDung;
}
