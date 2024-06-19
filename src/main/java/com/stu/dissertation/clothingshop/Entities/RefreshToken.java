package com.stu.dissertation.clothingshop.Entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="refresh_token",
        indexes = @Index(name = "idx_refresh_token",
                columnList = "refresh_token"))
public class RefreshToken extends BaseEntity {
    @Id
    private Long id;
    @Column(name = "refresh_token", columnDefinition = "VARCHAR(100) NOT NULL")
    private String refreshToken;
    @Column(name = "expires_at", columnDefinition = "BIGINT NOT NULL")
    private Long expiresAt;
    @OneToOne
    @MapsId
    @PrimaryKeyJoinColumn(name = "ma_nguoi_dung")
    private NguoiDung nguoiDung;
}
