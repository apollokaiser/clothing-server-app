package com.stu.dissertation.clothingshop.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Entity
@Table(name="user_token")
@NamedStoredProcedureQuery(
        name = "PROC_activate_user_by_token",
        procedureName = "PROC_activate_user_by_token",
        parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "user_token", type = String.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "validate_at", type = Long.class),
                @StoredProcedureParameter(mode = ParameterMode.OUT, name = "result", type = Integer.class)
        }
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserToken extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="token", columnDefinition = "VARCHAR(100) NOT NULL")
    private String token;
    @Column(name="validate_at", columnDefinition = "BIGINT")
    private Long validateAt;
    @Column(name="expires_at", columnDefinition = "BIGINT NOT NULL")
    private Long expiresAt;
    @ManyToOne
    @JoinColumn(name="ma_nguoi_dung",
            referencedColumnName = "ma_nguoi_dung",
            foreignKey = @ForeignKey(name="FK_user_token_nguoi_dung")
    )
    private NguoiDung nguoiDung;

}
