package com.stu.dissertation.clothingshop.Entities;

import jakarta.persistence.*;

@Entity
@Table(name="invalid_token",
        indexes = @Index(name="idx_invalid_token",
                        columnList = "token")
)
public class InvalidToken extends BaseEntity{

    @Id
    @Column(name="token", columnDefinition = "VARCHAR(64) NOT NULL")
    private String token;
    @Column(name="expiration", columnDefinition = "BIGINT NOT NULL")
    private Long expiration;
}
